package com.company;

public class CharOverflow {
    //￶
    //65526
    public static void main(String... args){
        char c = (char)-10;
        System.out.println(c);
        System.out.println((int)c);
    }
}
