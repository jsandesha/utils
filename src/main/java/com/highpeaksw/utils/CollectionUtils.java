package com.highpeaksw.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

    public static <T> Collection<T> subtractCollection( Collection<T> collectionOne, Collection<T> collectionTwo )
    {
        return org.apache.commons.collections4.CollectionUtils.subtract(collectionOne, collectionTwo);
    }

    public static void main(String[] args) {
        List<Integer> integerList1 = Arrays.asList(1, 2, 3);
        List<Integer> integerList2 = Arrays.asList(1,4);

        System.out.println(integerList1);
        System.out.println(integerList2);

        System.out.println(subtractCollection(integerList1, integerList2));
        System.out.println(subtractCollection(integerList2, integerList1));
    }
}
