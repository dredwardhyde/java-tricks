package com.company;

import java.math.BigDecimal;

public strictfp class Test {

    private static double d = 7.555d;

    public static strictfp void sout() {
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);
    }

    public static void nope() {
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);
    }

    public static void main(String[] args) {
        sout();
        nope();
        System.out.println(d);
    }

}
