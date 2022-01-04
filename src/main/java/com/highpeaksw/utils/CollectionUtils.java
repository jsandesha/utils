package com.highpeaksw.utils;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils()
    {
    }

    public static <T> Collection<T> subtractCollection( Collection<T> collectionOne, Collection<T> collectionTwo )
    {
        return org.apache.commons.collections4.CollectionUtils.subtract(collectionOne, collectionTwo);
    }
}
