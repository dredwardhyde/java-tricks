package com.company;

@SuppressWarnings("unused")
enum E implements Comparable<E> {
    A, B, C, D
}

enum M implements Comparable<M>, Cloneable, Interface {
    A {
        @Override
        public void test() {
            System.out.println("Overridden");
        }
    }, B;

//    @Override
//    public M clone(){ // final method, cannot override
//        return this;
//    }
//
//    @Override
//    public int compareTo(M that){ // final method, cannot override
//        return 0;
//    }

    @Override
    public void test() {
        System.out.println("General");
    }
}

@SuppressWarnings("all")
interface Interface {
    public void test();
}

public class EnumsWithInterfaces {
    public static void main(String... args) {
        M.A.test();
        M.B.test();
    }
}
