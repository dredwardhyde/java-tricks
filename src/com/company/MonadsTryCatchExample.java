package com.company;

public class MonadsTryCatchExample {
    public static void main(String[] args) throws Throwable{
        Integer value = Try.ofThrowable(() -> Integer.valueOf("1")).get();
        System.out.println(value); // 1

        int value2 = Try.ofThrowable(() -> Integer.valueOf("4")).flatMap(x -> Try.ofThrowable(() -> x / 2)).get();
        System.out.println(value2); // 2

        int value3 = Try.ofThrowable(() -> Integer.valueOf("wrong")).flatMap(x -> Try.ofThrowable(() -> x / 2)).orElse(8);
        System.out.println(value3); // 8
    }
}
