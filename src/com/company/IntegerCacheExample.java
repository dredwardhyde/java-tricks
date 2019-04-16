package com.company;

public class IntegerCacheExample {
    public static void main(String... args) {

        System.out.println(sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high")); // 1024


        System.out.println(Integer.valueOf(150) == new Integer(150)); // always false
        System.out.println(Integer.valueOf(110) == new Integer(110)); // always false

        System.out.println(Integer.valueOf(110) == 110); // always true
        System.out.println(Integer.valueOf(150) == 150); // always true
        System.out.println(Integer.valueOf(15000) == 15000); // always true

        System.out.println(Integer.valueOf(15000) == Integer.valueOf(15000)); // false because value is not in cache
        System.out.println(Integer.valueOf(110) == Integer.valueOf(110)); // true because value in cache

        System.out.println(Integer.valueOf("15000") == Integer.valueOf("15000")); // false because value is not in cache
        System.out.println(Integer.valueOf("110") == Integer.valueOf("110")); // true because value in cache

        System.out.println(Long.valueOf(15000) == Long.valueOf(15000)); // false because value is not in cache
        System.out.println(Long.valueOf(110) == Long.valueOf(110)); // true because value in cache

    }
}
