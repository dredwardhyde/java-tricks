package com.company;

import java.util.stream.Stream;

public class StreamMax {
    public static void main(String... args){
        /*
            Prints -1 because:

            Math.max returns biggest value passed,
            Stream.max expects comparator, which returns < 0 if first < second, 0 if first == second, > 0 otherwise

            -3, -2 => Math.max returns -2, Stream.max interprets as -3 < -2
            -2, -1 => Math.max returns -1, Stream.max interprets as -2 < -1
            -1, 0 => Math.max returns 0, Stream.max interprets as -1 == 0 - so -1 is the biggest value here
             0, 1 => Math.max returns 1, Stream.max interprets as 0 > 1
             1, 2 => Math.max returns 2, Stream.max interprets as 1 > 2
             2, 3 => Math.max returns 3, Stream.max interprets as 2 > 3
         */
        System.out.println(Stream.of(-3, -2, -1, 0, 1, 2, 3).max(Math::max).get());
    }
}
