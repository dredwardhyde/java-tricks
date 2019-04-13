package com.company;

import java.util.List;

public class TypeInferenceError {

    // why it compiles ok?
    public static void main(String... args) {
        String s = newList();
    }

    private static <T extends List<Integer>> T newList() {
        return null;
    }
}
