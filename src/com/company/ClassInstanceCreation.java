package com.company;

import java.util.HashMap;
import java.util.Map;

final class MyHashMap<K, V> extends HashMap<K, V> {}

public class ClassInstanceCreation {
    // https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.9
    public static void main(String... args) throws Exception{
        // Note: no diamond operator here (acc. JLS)
        String lol = "a";
        Map<String, String> myMap = new HashMap<String, String>(){{
           put("a", "b");
           put(lol, "d");

        }};
        Map<String, String> myMap2 = new HashMap<String, String>(){{
            put("a", "b");
            put("c", "d");
        }};

        // Can not inherit from final 'com.company.MyHashMap'
        // Map<String, String> myMap3 = new MyHashMap<String, String>(){{
        //    put("a", "b");
        //    put("c", "d");
        // }};

        // com.company.ClassInstanceCreation$1
        System.out.println(myMap.getClass().getName());
        // Ljava/lang/String; val$lol final synthetic
        System.out.println(myMap.getClass().getDeclaredField("val$lol").isSynthetic());
        // com.company.ClassInstanceCreation$2
        System.out.println(myMap2.getClass().getName());
    }
}
