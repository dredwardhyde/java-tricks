package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ClassA<T>{
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

// class first, then interfaces
class ClassB<T extends Number & Serializable & Cloneable>{
    private T t;
    private Number number;
    private Serializable serializable;
    private Cloneable cloneable;

    public void set(T t) {
        this.t = t;
        this.number = t; // guaranteed
        this.serializable = t;
        this.cloneable = t;
    }
    public T get() { return t; }
}

// class ClassC<T super Number> { } -- super is not allowed here also

public class GenericsExplained {

    // public <T super Number> void fill(T list) {} -- compile time error because T could be any type up to Object,
    // only methods of Object are guaranteed

    public <T extends Number> void fill(T list) {} // methods of Number are guaranteed

    public static void main(String... args){
        // what compiler should see - Lists of all types that extends Number and Number itself
        List<? extends Number> myNums3 = new ArrayList<Integer>();
        List<? extends Number> myNums4 = new ArrayList<Float>();
        List<? extends Number> myNums5 = new ArrayList<Double>();
        List<? extends Number> myNums6 = new ArrayList<Number>();

        // only common element for all allowed type parameters above is null
        // can not add Integer because actual type could be different type, eq Float, same for other types
        // can not add Number because downcasting to child class eq Integer or Double is wrong
        myNums3.add(null);


        List<? super Number> myNums; // here compiler should allow us assign List all superclasses of Number and Number itself
        myNums = new ArrayList<Object>();
        myNums = new ArrayList<Number>();
        myNums.add(10);   // Integer - safe to add because upcasting to Object or Number
        myNums.add(3.14); // Double - same as Integer
        myNums.add(null); // null, Number - null - special case, Number - add as is or upcast to Object
        // myNums.add(new Object()); // wrong because you can not downcast Object to Number



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
