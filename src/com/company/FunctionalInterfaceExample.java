package com.company;

interface TestInterface<T> {
    default void functionalOne(String test) {
        System.out.println(test);
    }

    //void functionalOne(T test); //-- see explanation below

    void functionalTwo(T test2);

    void functionalTwo(String test2);
}

@FunctionalInterface
// com.company.DerivedFunctionalInterface inherits abstract and default for functionalOne(String)
// from types com.company.TestInterface and com.company.TestInterface

// https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html

// 9.4.1.3. Inheriting Methods with Override-Equivalent Signatures
// If an interface I inherits a default method whose signature is override-equivalent with another method inherited by I,
// then a compile-time error occurs. (This is the case whether the other method is abstract or default.)
interface DerivedFunctionalInterface extends TestInterface<String> {
}

class DerivedClassString implements TestInterface<String> { // ok, we need only to implement only 2 methods

    @Override
    public void functionalTwo(String test2) {

    }

    @Override
    public void functionalOne(String test) {

    }
}

class DerivedClassNumber implements TestInterface<Number> { // we need to implement all 4 methods

    @Override
    public void functionalTwo(Number test2) {

    }

//    @Override
//    public void functionalOne(Number test) {
//
//    }

    @Override
    public void functionalOne(String test) {

    }

    @Override
    public void functionalTwo(String test2) {

    }
}
