package com.highpeaksw.utils;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;

public class StringUtils {

    private StringUtils() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.CONSTRUCTOR_CREATION_ERROR,
                HttpStatus.BAD_REQUEST);
    }

    public static String convertListOfStringToAStringWithDelimiter( List<String> stringList, String startPrefix,
            String endPrefix, char delimiter )
    {
        StringBuilder stringBuilder = new StringBuilder();
        for( String s : stringList )
        {
            stringBuilder.append(startPrefix);
            stringBuilder.append(s);
            stringBuilder.append(endPrefix);
            stringBuilder.append(delimiter);
        }

        String output = stringBuilder.toString();
        return NullEmptyUtils.isNullOrEmpty(output) ? output : org.apache.commons.lang3.StringUtils.chop(output);
    }
}
