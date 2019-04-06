package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamLateBinding {
    public static void main(String... args){
        List<String> list = new ArrayList<>();
        list.add("milk");
        list.add("bread");
        list.add("butter");
        Stream<String> stream = list.stream();
        list.add("eggs");
        /*
            Late binding occurred, prints:

            milk
            bread
            butter
            eggs
         */
        stream.forEach(System.out::println);
        System.out.println();

        List<String> list2 = new ArrayList<>();
        list2.add("milk");
        list2.add("bread");
        list2.add("butter");
        list2 = list2.subList(0, 2); // no butter!
        Stream<String> stream2 = list2.stream();
        list2.add("eggs");
        // https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8148748
        stream2.forEach(System.out::println); // java.util.ConcurrentModificationException
    }
}
