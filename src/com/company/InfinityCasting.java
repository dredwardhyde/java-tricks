package com.company;

import java.util.Objects;

public class InfinityCasting {
    public static void main(String... args){
        // Java's Double.NaN is one particular value that will be interpreted as NaN, but there are 2^53−3 others.
        print(Double.NaN);                  // 0111111111111000000000000000000000000000000000000000000000000000  NaN has at least one bit in the mantissa set to 1
        print(Double.NEGATIVE_INFINITY);    // 1111111111110000000000000000000000000000000000000000000000000000 -Inf has all bits of the mantissa zero, sign bit 1
        print(Double.POSITIVE_INFINITY);    // 0111111111110000000000000000000000000000000000000000000000000000 +Inf has all bits of the mantissa zero, sign bit 0
        print(-Double.MAX_VALUE);           // 1111111111101111111111111111111111111111111111111111111111111111
        print(Double.MAX_VALUE);            // 0111111111101111111111111111111111111111111111111111111111111111
        System.out.println((short)Double.POSITIVE_INFINITY); // prints -1
        System.out.println((short)Double.NEGATIVE_INFINITY); // prints 0
        // to stage casting Double -> Integer -> Short

        // Double -> Integer
        System.out.println(Integer.toBinaryString((int)Double.POSITIVE_INFINITY)); // 01111111111111111111111111111111 Integer.MAX_VALUE
        System.out.println(Integer.toBinaryString((int)Double.NEGATIVE_INFINITY)); // 10000000000000000000000000000000 Integer.MIN_VALUE

        // Integer -> Short
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE)); // 01111111111111111111111111111111 Integer.MAX_VALUE
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE)); // 10000000000000000000000000000000 Integer.MIN_VALUE

        // Integer -> Short
        System.out.println((short)Integer.MAX_VALUE); // 01111111_11111111_11111111_11111111 Integer.MAX_VALUE -> 11111111_11111111 == -1 short
        System.out.println((short)Integer.MIN_VALUE); // 10000000_00000000_00000000_00000000 Integer.MIN_VALUE -> 00000000_00000000 == 0 short

        System.out.println(Double.NaN == Double.NaN); // always false because contract
        System.out.println(Objects.equals(Double.NaN, Double.NaN)); // always true because Objects.equals calls a.equals(b) and Double.equals compare bits

        System.out.println(0d == -0d); // always true
        System.out.println(Objects.equals(0d, -0d)); // always false, because Double.equals() compare bits
        print(0d); // is 0000000000000000000000000000000000000000000000000000000000000000
        print(-0d);// is 1000000000000000000000000000000000000000000000000000000000000000
    }

    private static void print(double d){
        System.out.println(Long.toBinaryString(Double.doubleToRawLongBits(d)));
    }
}
