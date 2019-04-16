package com.company;

class TestClassModifiers { // public or package-private + final, abstract
    public static void testMethodOne() {
        class Local {
        } // package-private + final, abstract
    }

    public void testMethodTwo() {
        class Local {
        } // package-private + final, abstract

    }

    public class TestInner { // public, private, protected, package-private + static, final, abstract

    }
}
