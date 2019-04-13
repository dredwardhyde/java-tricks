package com.company;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftAndWeakReferenceExample {
    /*
        Lol
        1350000
        null
        hello, world
        null
     */
    public static void main(String... args) {
        StringBuilder builder = new StringBuilder("Lol");
        SoftReference<StringBuilder> reference1 = new SoftReference<>(builder);
        builder = null;
        System.out.println(reference1.get());
        long[] array = new long[1_350_000];
        System.out.println(array.length);
        System.gc();
        System.out.println(reference1.get());

        String str = new String("hello, world"); // create with "new" so String will not be interned
        WeakReference<String> ref = new WeakReference<>(str);
        str = null;
        System.out.println(ref.get());
        System.gc();
        System.out.println(ref.get());
    }
}
