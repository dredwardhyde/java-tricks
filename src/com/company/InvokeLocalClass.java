package com.company;

import java.util.Optional;

interface First {
    default void doFirst() {
        System.out.println("Do first");
    }
}

interface Second {
    default void doSecond() {
        System.out.println("Do second");
    }
}

/*
    Do first
    Do second

    Do first
    Do second
 */
public class InvokeLocalClass {
    public static void main(String... args) {
        class FirstAndSecond implements First, Second {
        }
        test(new FirstAndSecond());
    }

    private static void test(Object obj) {
        Optional.of((First & Second) obj).ifPresent(x -> {
            x.doFirst();
            x.doSecond();
        });

        System.out.println();

        bridge((First & Second) obj).doFirst();
        bridge((First & Second) obj).doSecond();
    }

    private static <T extends First & Second> T bridge(T obj) {
        return obj;
    }
}
