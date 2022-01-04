package com.highpeaksw.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;

import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;

public class NullEmptyUtils {

    private NullEmptyUtils() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.CONSTRUCTOR_CREATION_ERROR,
                HttpStatus.BAD_REQUEST);
    }

    public static boolean isNull( Object val )
    {
        return val == null;
    }

    public static boolean isNullOrEmpty( String val )
    {
        return isNull(val) || val.trim().isEmpty();
    }

    public static boolean isNullOrEmpty( Boolean val )
    {
        return isNull(val) || !val;
    }

    public static boolean isNullOrEmpty( List<?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    public static boolean isNullOrEmpty( Collection<?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    public static boolean isNullOrEmpty( Number val )
    {
        return isNull(val) || val.doubleValue() == 0.0D || val.intValue() == 0;
    }

    public static boolean isNullOrZero( Number val )
    {
        return isNull(val) || val.doubleValue() == 0;
    }

    public static boolean isNullOrEmpty( Map<?, ?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    public static void throwExceptionIfInputIsNull( Object object ) throws DataException
    {
        if( isNull(object) )
        {
            throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.NULL_INPUT_ERROR,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void throwExceptionIfInputIsNull( Object object, String message ) throws DataException
    {
        if( isNull(object) )
        {
            throw new DataException(GeneralConstants.EXCEPTION, message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void throwExceptionIfInputIsNullOrEmpty( Object object ) throws DataException
    {
        if( isNull(object) || (object instanceof String s && isNullOrEmpty(s))
                || (object instanceof Collection<?> c && c.isEmpty()) )
        {
            throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.NULL_INPUT_ERROR,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void throwExceptionIfInputIsNullOrEmpty( Object object, String message ) throws DataException
    {
        if( isNull(object) || (object instanceof String s && isNullOrEmpty(s))
                || (object instanceof Collection<?> c && c.isEmpty()) )
        {
            throw new DataException(GeneralConstants.EXCEPTION, message, HttpStatus.BAD_REQUEST);
        }
        if( object instanceof String s && isNullOrEmpty(s) )
        {
            throw new DataException(GeneralConstants.EXCEPTION, message, HttpStatus.BAD_REQUEST);
        }
        if( object instanceof Collection c && c.isEmpty() )
        {
            throw new DataException(GeneralConstants.EXCEPTION, message, HttpStatus.BAD_REQUEST);
        }
    }
}
