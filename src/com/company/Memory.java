package com.company;

import sun.misc.Unsafe;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;


public class Memory {

    public static void main(String[] args) throws Exception {

        com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        System.out.println("Before allocation:");
        System.out.println(mxbean.getTotalPhysicalMemorySize() / (1024 * 1024) + " MB TOTAL");
        System.out.println(mxbean.getFreePhysicalMemorySize() / (1024 * 1024) + " MB FREE");

        long maximum = Integer.MAX_VALUE + 1L;
        DirectIntArray.allocate(maximum);

        System.out.println("After allocation:");
        System.out.println(mxbean.getTotalPhysicalMemorySize() / (1024 * 1024) + " MB TOTAL");
        System.out.println(mxbean.getFreePhysicalMemorySize() / (1024 * 1024) + " MB FREE");

        DirectIntArray.setValue(0L, 10);
        DirectIntArray.setValue(maximum, 20);

        DirectIntArray.setValueUnsafe(10, 15);

        System.out.println(DirectIntArray.getValue(0L));
        System.out.println(DirectIntArray.getValue(maximum));
        DirectIntArray.destroy();

        System.out.println("After destroy:");
        System.out.println(mxbean.getTotalPhysicalMemorySize() / (1024 * 1024) + " MB TOTAL");
        System.out.println(mxbean.getFreePhysicalMemorySize() / (1024 * 1024) + " MB FREE");
    }

    private static class DirectIntArray {

        private final static long INT_SIZE_IN_BYTES = 4;

        private static long startIndex;

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

        public static void allocate(long size) {
            startIndex = unsafe.allocateMemory(size * INT_SIZE_IN_BYTES);
            unsafe.setMemory(startIndex, size * INT_SIZE_IN_BYTES, (byte) 0);
        }

        public static void setValue(long index, int value) {
            unsafe.putInt(index(index), value);
        }

        public static void setValueUnsafe(long index, int value) {
            unsafe.putInt(index, value);
        }

        public static int getValue(long index) {
            return unsafe.getInt(index(index));
        }

        private static long index(long offset) {
            return startIndex + offset * INT_SIZE_IN_BYTES;
        }

        public static void destroy() {
            unsafe.freeMemory(startIndex);
        }
    }
}
