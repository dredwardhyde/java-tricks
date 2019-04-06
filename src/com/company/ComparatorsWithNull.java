package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComparatorsWithNull {
    public static void main(String... args){
        List<String> list = Arrays.asList("String 1", "String 2", "String 3");
        Comparator<String> cmp = Comparator.nullsLast(Comparator.naturalOrder());

        System.out.println(Collections.max(list, cmp));                         // String 3
        System.out.println(list.stream().collect(Collectors.maxBy(cmp)).get()); // String 3
        System.out.println(list.stream().max(cmp).get());                       // String 3


        List<String> list2 = Arrays.asList("String 1", null, "String 3");

        System.out.println(Collections.max(list2, cmp));                         // null
        System.out.println(list2.stream().collect(Collectors.maxBy(cmp)).get()); // java.util.NoSuchElementException: No value present - collect returns empty Optional
        System.out.println(list2.stream().max(cmp).get());                       // java.lang.NullPointerException - .max returns null
    }
}
