package com.company;

import org.eclipse.collections.impl.block.function.primitive.IntCaseFunction;
import java.util.stream.IntStream;

public class FizzBuzz {
    public static void main(String... args) {
        // Java 8 with Streams
        IntStream.rangeClosed(0, 100).mapToObj(
                i -> i % 3 == 0 ?
                        (i % 5 == 0 ? "FizzBuzz" : "Fizz") :
                        (i % 5 == 0 ? "Buzz" : i))
                .forEach(System.out::println);

        // Eclipse Collections
        IntStream.rangeClosed(0, 100).mapToObj(
                new IntCaseFunction<>(Integer::toString)
                        .addCase(i -> i % 15 == 0, e -> "FizzBuzz")
                        .addCase(i -> i % 3 == 0, e -> "Fizz")
                        .addCase(i -> i % 5 == 0, e -> "Buzz"))
                .forEach(System.out::println);
    }
}
