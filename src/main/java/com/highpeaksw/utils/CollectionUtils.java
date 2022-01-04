package com.highpeaksw.utils;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;

public class CollectionUtils {

    private CollectionUtils() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.CONSTRUCTOR_CREATION_ERROR,
                HttpStatus.BAD_REQUEST);
    }

    public static <T> Collection<T> subtractCollection( Collection<T> collectionOne, Collection<T> collectionTwo )
    {
        return org.apache.commons.collections4.CollectionUtils.subtract(collectionOne, collectionTwo);
    }
}
