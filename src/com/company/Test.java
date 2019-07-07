package com.company;

import java.math.BigDecimal;

public class Test {

    private static double d = 7.00000000000000000000000001d;

    public static strictfp void sout() {
        BigDecimal bigDecimal = new BigDecimal(2.0 - 1.1);
        System.out.println(bigDecimal);
    }

    public static strictfp void sout2() {
        System.out.println(2.0 - 1.1);
    }

    public static void nope() {
        System.out.println(2.0 - 1.1);
    }

    /*
        0.899999999999999911182158029987476766109466552734375
        0.8999999999999999
        0.8999999999999999
     */
    public static void main(String[] args) {
        sout();
        sout2();
        nope();
    }
}
