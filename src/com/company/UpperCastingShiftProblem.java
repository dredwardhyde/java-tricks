package com.company;

public class UpperCastingShiftProblem {

    // -1 in binary is 11111111_11111111
    static short bitCount(short s){
        short bits = 0;
        // here we upper-cast s to int
        // s is 11111111_11111111_11111111_11111111
        while(s != 0){
            bits += s & 1;
            // s is 01111111_11111111_11111111_11111111
            // (short)s is 11111111_11111111 again
            s >>>= 1;
        }
        return bits;
    }

    public static void main(String... args){
        // Java uses two's complement for negative numbers and
        // the basic rule is to take the positive, invert all bits then add one. That gets you the negative.
        // In two's-complement, there is only one zero, represented as 00000000000000000000000000000000.
        // https://en.wikipedia.org/wiki/Signed_number_representations#Two.27s_complement
        // eq 5          is 00000000000000000000000000000101
        //    5 inverted is 11111111111111111111111111111010
        //    adding one is 11111111111111111111111111111011
        //   -5          is 11111111111111111111111111111011
        System.out.println(Integer.toBinaryString(-1));             // 11111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-2));             // 11111111111111111111111111111110
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE)); // 10000000000000000000000000000000
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE)); // 01111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(bitCount((short)-1));
    }
}
