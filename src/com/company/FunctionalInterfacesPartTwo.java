package com.company;


@FunctionalInterface
interface OriginalPredicate<T>{
    boolean test(T o);
}


@FunctionalInterface
interface CopyPredicate {
    <T> boolean test(T o);
}

public class FunctionalInterfacesPartTwo {
    public static void main(String... args){
        OriginalPredicate<Object> lambda = (Object obj) -> "test".equals(obj);
        OriginalPredicate<Object> methodRef = "test"::equals;

        // CopyPredicate lambda2 = (Object obj) -> "test".equals(obj); // lambda can not implement generic method
        CopyPredicate methodRef2 = "test"::equals;
    }
}
