package com.highpeaksw.utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.crypto.tink.subtle.AesGcmJce;
import com.highpeaksw.utils.NullEmptyUtils;
import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

    /**
     * This method uses the popular google crypto API {@link com.google.crypto.tink} to encrypt the
     * {@param plainText} using the key {@param key}. The key size must be 16 characters for 128-bit
     * encryption and 32 characters for 256-bit encryption. The method returns the encrypted value
     * encoding in Base-64 format
     * 
     * @param plainText
     *            Text to be AES encrypted
     * @param key
     *            Key to be used for encryption
     * @return Base64 Encoded AES encrypted string
     * @throws DataException
     *             missing any validation
     */
    @Override
    public String aesEncrypt( String plainText, String key ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(key, "Encryption key is missing");
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(plainText, "Plain text is missing");

            AesGcmJce agjEncryption = new AesGcmJce(key.getBytes());
            byte[] encrypted = agjEncryption.encrypt(plainText.getBytes(), null);
            return Base64.getEncoder().encodeToString((encrypted));
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( GeneralSecurityException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch( Exception e )
        {
            log.error("Error {0}", e);
            throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.SOMETHING_WENT_WRONG,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String aesDecrypt( String encryptedText, String key ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(key, "Encryption key is missing");
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(encryptedText, "Encrypted text is missing");
            AesGcmJce agjDecryption = new AesGcmJce(key.getBytes());
            return new String(agjDecryption.decrypt(Base64.getDecoder().decode(encryptedText), null),
                    StandardCharsets.UTF_8);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( GeneralSecurityException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch( Exception e )
        {
            log.error("Error {0}", e);
            throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.SOMETHING_WENT_WRONG,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
