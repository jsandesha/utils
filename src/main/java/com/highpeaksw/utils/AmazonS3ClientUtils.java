package com.highpeaksw.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;

import java.net.URL;

import org.springframework.mock.web.MockMultipartFile;

import java.util.Date;

@Slf4j
public class AmazonS3ClientUtils {

    private static final String AMAZON_S3_BEAN_NULL_ERROR = "Amazon S3 configuration bean cannot be null";

    private static final String S3_BUCKET_NAME_NULL_ERROR = "S3 bucket name is missing";

    private static final String FILE_NULL_ERROR = "File to be uploaded is missing";

    private static final String FILE_PATH_NULL_ERROR = "File path is missing";

    private AmazonS3ClientUtils() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.CONSTRUCTOR_CREATION_ERROR,
                HttpStatus.BAD_REQUEST);

    }

    /**
     * Method to upload file to S3
     *
     * @param amazonS3
     *            amazon s3 config bean {@link AmazonS3}
     * @param bucketName
     *            s3 bucket name
     * @param file
     *            file to be uploaded to s3 {@link MultipartFile}
     * @param filePath
     *            file path to which file is to be uploaded in s3
     * @return file path to which file is uploaded
     * @throws DataException
     *             customer exception
     */
    public static String uploadFile( AmazonS3 amazonS3, String bucketName, MultipartFile file, String filePath )
            throws DataException
    {
        try
        {

            NullEmptyUtils.throwExceptionIfInputIsNull(amazonS3, AMAZON_S3_BEAN_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(bucketName, S3_BUCKET_NAME_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNull(file, FILE_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(filePath, FILE_PATH_NULL_ERROR);

            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(file.getContentType());
            data.setContentLength(file.getSize());

            amazonS3.putObject(bucketName, filePath, file.getInputStream(), data);

            return filePath;

        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while uploading file to S3",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to delete a file from S3.
     *
     * @param amazonS3
     *            amazon s3 config bean {@link AmazonS3}
     * @param bucketName
     *            s3 bucket name
     * @param filePath
     *            file path to which file is to be uploaded in s3
     * @throws DataException
     *             customer exception
     */
    public static void deleteFile( AmazonS3 amazonS3, String bucketName, String filePath ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(amazonS3, AMAZON_S3_BEAN_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(bucketName, S3_BUCKET_NAME_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(filePath, FILE_PATH_NULL_ERROR);

            amazonS3.deleteObject(bucketName, filePath);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while deleting file from S3",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get pre-signed URL to download/render file.
     *
     * @param amazonS3
     *            amazon s3 config bean {@link AmazonS3}
     * @param bucketName
     *            s3 bucket name
     * @param filePath
     *            file path to which file is to be uploaded in s3
     * @param expirationTime
     *            date-time at which URL should be expired
     * @param isDownload
     *            flag to check if file to be downloaded/ rendered from URL
     * @return file URL
     * @throws DataException
     *             customer exception
     */
    public static URL generatePreSignedUrlToGetObjects( AmazonS3 amazonS3, String bucketName, String filePath,
            Date expirationTime, Boolean isDownload ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(amazonS3, AMAZON_S3_BEAN_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(bucketName, S3_BUCKET_NAME_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(filePath, FILE_PATH_NULL_ERROR);
            if( NullEmptyUtils.isNull(expirationTime) )
            {
                // Set the pre-signed URL to expire after 20 min.
                expirationTime = new java.util.Date();
                long expTimeMillis = expirationTime.getTime();
                expTimeMillis += 1000 * 60 * 20;
                expirationTime.setTime(expTimeMillis);
            }

            log.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
                    filePath).withMethod(HttpMethod.GET).withExpiration(expirationTime);

            // render works only for PDF files.
            if( !NullEmptyUtils.isNull(isDownload) && isDownload )
            {
                ResponseHeaderOverrides responseHeaderOverrides = new ResponseHeaderOverrides();
                responseHeaderOverrides.setContentDisposition("form-data");
                responseHeaderOverrides.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

                generatePresignedUrlRequest.setResponseHeaders(responseHeaderOverrides);
            }
            return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION,
                    "Error while generating pre-signed URL for a file from S3", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get multipart file from s3 bucket
     *
     * @param amazonS3
     *            amazon s3 config bean {@link AmazonS3}
     * @param bucketName
     *            s3 bucket name
     * @param filePath
     *            file path to which file is to be uploaded in s3
     * @return multipart file
     * @throws DataException
     *             customer exception
     */
    public static MultipartFile getFileFromS3( AmazonS3 amazonS3, String bucketName, String filePath )
            throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(amazonS3, AMAZON_S3_BEAN_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(bucketName, S3_BUCKET_NAME_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(filePath, FILE_PATH_NULL_ERROR);

            // fetch object from s3
            S3Object s3object = amazonS3.getObject(bucketName, filePath);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            return new MockMultipartFile("file", filePath.substring(filePath.lastIndexOf("/")), "text/plain",
                    byteArray);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while getting multipart file from S3",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
