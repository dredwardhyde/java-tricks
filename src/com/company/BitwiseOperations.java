package com.company;

public class BitwiseOperations {
    public static void main(String... args) {
        int bitmask = 0x000F;
        int bitmask2 = 0x00F0;
        int val = 0x2222; // 2 + 2 * 16 + 2 * 16 * 16 + 2 * 16 * 16 * 16 = 2 + 32 + 512 + 8192 = 8738
        System.out.println(val);
        System.out.println((val & bitmask)  == 0x0002); // - 0x0002 = 2
        System.out.println((val & bitmask2) == 0x0020); // - 0x0022 = 32

        System.out.println(abs(0) == Math.abs(0));
        System.out.println(abs(-0) == Math.abs(0));
        System.out.println(abs(1) == Math.abs(1));
        System.out.println(abs(-1) == Math.abs(1));
        System.out.println(abs(-100) == Math.abs(100));
        System.out.println(abs(100) == Math.abs(100));
        System.out.println(abs(Integer.MIN_VALUE) == Math.abs(Integer.MIN_VALUE));
    }

    private static int abs(int i){
        if(i >>> 31 == 1) {
            return ~i + 1;
        }
        return i;
    }
}
