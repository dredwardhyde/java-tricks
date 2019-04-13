package com.company;

import sun.misc.Unsafe;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class SizeOf {

    private static Unsafe unsafe = null;

    static {
        try {
            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            unsafe = unsafeConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long sizeOf(Class<?> clazz) {
        long maximumOffset = 0;
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    maximumOffset = Math.max(maximumOffset, unsafe.objectFieldOffset(f));
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return maximumOffset + 8;
    }

    public static long actualSize(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray().length;
    }

    public static WriteResult copyFromHeapToOffHeap(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        byte[] object = baos.toByteArray();
        long start = unsafe.allocateMemory(object.length);
        long tmp = start;
        System.out.println("Start: " + start);
        for (byte b : object) {
            unsafe.putByte(start, b);
            start++;
        }
        baos.close();
        System.out.println("End: " + start + " written: " + (start - tmp));
        return new WriteResult(start, object.length);
    }


    public static void main(String[] args) throws Exception {
        HashMap<String, String> map1 = new HashMap<>();
        System.out.println(sizeOf(map1.getClass()));
        map1.put("lol", "kek");
        map1.put("lol1", "kek2");
        map1.put("lol3", "kek3");
        long start = System.nanoTime();
        System.out.println(sizeOf(map1.getClass()) + " in " + (System.nanoTime() - start) / 1000000000.0);
        start = System.nanoTime();
        System.out.println("Get object with instrument: " + ObjectSizeFetcher.getObjectSize(map1) + " in " + (System.nanoTime() - start) / 1000000000.0);
        start = System.nanoTime();
        System.out.println("Actual size:" + actualSize(map1) + " in " + (System.nanoTime() - start) / 1000000000.0);
        start = System.nanoTime();
        WriteResult result = copyFromHeapToOffHeap(map1);
        System.out.println("Written in " + (System.nanoTime() - start) / 1000000000.0 + "s. end: " + result.getOffset() + " size: " + result.getSize());
        WriteResult result2 = copyFromHeapToOffHeap(map1);
        System.out.println("Written in " + (System.nanoTime() - start) / 1000000000.0 + "s. end: " + result2.getOffset() + " size: " + result2.getSize());

    }
}
