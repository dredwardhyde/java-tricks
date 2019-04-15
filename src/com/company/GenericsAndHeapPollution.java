package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericsAndHeapPollution {

    static long sum(Number[] numbers) {
        long summation = 0;
        for(Number number : numbers) {
            summation += number.longValue();
        }
        return summation;
    }


    static long sum(List<Number> numbers) {
        long summation = 0;
        for(Number number : numbers) {
            summation += number.longValue();
        }
        return summation;
    }

    public static void main(String... args) {
        Integer[] myInts = {1, 2, 3, 4};
        Number[] myNumber = myInts;

        try {
            myNumber[0] = 3.14;
        } catch (ArrayStoreException e) {
            /*
                This means that you can fool the compiler, but you cannot fool the runtime type system.
                And this is so because arrays are what we call reifiable types.
                This means that at runtime Java knows that this array was actually instantiated as an array of integers
                which simply happens to be accessed through a reference of type Number[].
             */
            System.out.println("Heap pollution: " + e.getMessage());
        }

        /*
            Now, the problem with Java generic types is that the type information is discarded by the compiler
            and it is not available at run time. This process is called type erasure.
            There are good reason for implementing generics like this in Java, but that's a long story,
            and it has to do with binary compatibility with pre-existing code.

            But the important point here is that since, at runtime there is no type information,
            there is no way to ensure that we are not committing heap pollution.
         */

        List<Integer> myIntsList = new ArrayList<Integer>();
        myIntsList.add(1);
        myIntsList.add(2);

//        List<Number> myNums = myIntsList; //compiler error not to allow you store anything but Integer to myIntsList
//        myNums.add(3.14); //heap polution

        /*
            If the Java compiler does not stop you from doing this, the runtime type system cannot stop you either,
            because there is no way, at runtime, to determine that this list was supposed to be a list of integers only.
            The Java runtime would let you put whatever you want into this list, when it should only contain integers,
            because when it was created, it was declared as a list of integers.

            As such, the designers of Java made sure that you cannot fool the compiler.
            If you cannot fool the compiler (as we can do with arrays) you cannot fool the runtime type system either.

            As such, we say that generic types are non-reifiable.
         */

        Integer[] myInts1 = {1,2,3,4,5};
        Long[] myLongs = {1L, 2L, 3L, 4L, 5L};
        Double[] myDoubles = {1.0, 2.0, 3.0, 4.0, 5.0};

        System.out.println(sum(myInts1));
        System.out.println(sum(myLongs));
        System.out.println(sum(myDoubles));

        List<Integer> myInts2 = Arrays.asList(1,2,3,4,5); // -> List of objects in bytecode
        List<Long> myLongs2 = Arrays.asList(1L, 2L, 3L, 4L, 5L); // -> List of objects in bytecode
        List<Double> myDoubles2 = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0); // -> List of objects in bytecode

//       System.out.println(sum(myInts2)); //compiler error, because target type is Number during compile time
        // and Object in runtime, you can't even cast to List<Number>, because then you will be able
        // to add at runtime Double to list of Integers
        // and will get runtime exception - type of exceptions that generics suppose to prevent
//        System.out.println(sum(myLongs2)); //compiler error
//        System.out.println(sum(myDoubles2)); //compiler error

        // The solution is to learn to use two powerful features of Java generics known as covariance and contravariance.

        /*
            Covariance
            With covariance you can read items from a structure, but you cannot write anything into it.
            All these are valid declarations.
         */
        List<? extends Number> myNums3 = new ArrayList<Integer>();
        List<? extends Number> myNums4 = new ArrayList<Float>();
        List<? extends Number> myNums5 = new ArrayList<Double>();

        // And you can read from myNums, because you can be sure that whatever the actual list contains,
        // it can be upcasted to a Number (after all anything that extends Number is a Number, right?)

        // However, you are not allowed to put anything into a covariant structure.
        Number n = myNums3.get(0);
//        myNums3.add(45); //compiler error add (capture<? extends java.lang.Number>) in List cannot be applied to (int)
        // adding Integer is valid only for List<Number> and List<Integer>, List<Long>, but not List<Double>
        myNums3.add(null); // allowed because of reasons ))
        // This would not be allowed, because Java cannot guarantee what is the actual type of the object in the generic
        // structure. It can be anything that extends Number, but the compiler cannot be sure. So you can read, but not write.

        // Contravariance
        //
        // With contravariance you can do the opposite.
        // You can put things into a generic structure, but you cannot read out from it.

        List<Object> myObjs = new ArrayList<>();
        myObjs.add("Luke");
        myObjs.add("Obi-wan");

        List<? super Number> myNums = myObjs; // you could add all that ? extends Number
        myNums.add(10); // Integer
        myNums.add(3.14); // Double
        myNums.add(null); // null, Number

        // In this case, the actual nature of the object is a List of Objects, and through contravariance,
        // you can put Numbers into it, basically because all numbers have Object as their common ancestor.
        // As such, all Numbers are objects, and therefore this is valid.
        //
        // However, you cannot safely read anything from this contravariant structure assuming that you will get a number.

        // Incompatible types.
        // Required: java.lang.Number
        // Found: capture<? super java.lang.Number>
        // Number myNum = myNums.get(0);

        // As you can see, if the compiler allowed you to write this line, you would get a ClassCastException at runtime.


        List<Integer> myInts4 = Arrays.asList(1,2,3,4);
        List<Double> myDoubles4 = Arrays.asList(3.14, 6.28);
        List<Object> myObjs4 = new ArrayList<>();

        copy(myInts4, myObjs4);
        copy(myDoubles4, myObjs4);
    }

    // Get/Put Principle
    //
    // As such, use covariance when you only intend to take generic values out of a structure,
    // use contravariance when you only intend to put generic values into a structure
    // and use the exact generic type when you intend to do both.
    //
    // The best example I have is the following that copies any kind of numbers
    // from one list into another list. It only gets items from the source, and it only puts items in the destiny.

    public static void copy(List<? extends Number> source, List<? super Number> destiny) {
        for(Number number : source) {
            destiny.add(number);
        }
    }
}
