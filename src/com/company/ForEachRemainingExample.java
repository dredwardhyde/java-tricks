package com.company;

import java.util.*;

public class ForEachRemainingExample {

    private static void killThemAll(Collection<String> collection){
        Iterator<String> strings = collection.iterator();
        strings.forEachRemaining(e -> {
            if(strings.hasNext()){
                strings.next();
                strings.remove();
            }
        });
        System.out.println(Arrays.toString(collection.toArray()));
    }

    /*  https://docs.oracle.com/javase/8/docs/api/?java/util/Collection.html

        default void forEachRemaining(Consumer<? super E> action)
        Performs the given action for each remaining element until all elements have been
        processed or the action throws an exception. Actions are performed in the order of iteration,
        if that order is specified. Exceptions thrown by the action are relayed to the caller.

        Implementation Requirements:
        The default implementation behaves as if:

        while (hasNext())
            action.accept(next());
    */
    public static void main(String... args){
        // ArrayList overrides default forEachRemaining to iterate over every element
        // it ignores all changes for current position in hasNext() and next() and works only with local vars
        killThemAll(new ArrayList<>(Arrays.asList("N", "S", "W", "S", "L", "S", "L", "V")));    // []
        // LinkedList shares variables with next() and hasNext()
        killThemAll(new LinkedList<>(Arrays.asList("N", "S", "W", "S", "L", "S", "L", "V")));   // [S, S, S, V]
        killThemAll(new ArrayDeque<>(Arrays.asList("N", "S", "W", "S", "L", "S", "L", "V")));   // [N, S, W, S, L, S, L, V]
        killThemAll(new TreeSet<>(Arrays.asList("N", "S", "W", "S", "L", "S", "L", "V")));      // [L, S, W]
    }
}
