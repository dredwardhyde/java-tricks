package com.company;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysSetAll {
    public static void main(String... args) throws Exception {
        List[] lists = new List[2];
        // setAll(double[] array, IntFunction generator)
        // array - array to be initialized
        // generator - a function accepting an index and producing the desired value for that position
        Arrays.setAll(lists, ArrayList::new);
        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);
        System.out.println(((Object[]) field.get(lists[0])).length); // 0
        System.out.println(((Object[]) field.get(lists[1])).length); // 1
    }
}
