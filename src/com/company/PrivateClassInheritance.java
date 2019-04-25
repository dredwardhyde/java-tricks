package com.company;

public class PrivateClassInheritance {
    public static void main(String... args) {
        new PrivateClassInheritance().new B();
    }

    private class A {

        private int lol;

        private A() {
            System.out.println("A");
        }

        private void lol() {
            System.out.println("lol");
        }
    }

    class B extends A {
        B() {
            super(); // access to private constructor
            super.lol(); // access to private method
            // this.lol; // but no access to private field
            System.out.println("B");
        }
    }
}
