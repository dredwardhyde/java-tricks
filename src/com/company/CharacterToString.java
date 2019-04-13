package com.company;

import java.util.Objects;

public class CharacterToString {
    public static void main(String... args){
        System.out.println("Character c is " + 'c');    // Character c is c
        System.out.println("Character 2 is " + '2');    // Character 2 is 2
        System.out.println((int)'2');                   // 50
        // https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.15.3
        System.out.println("Character 2 is " + +'2');   // Character 2 is 50 -- firs + is concat,
        // second + is unary plus that converts char to int
    }
}
