package com.company;

class Other {
    static String hello = "Hello";
}

public class StringLiterals {
    // true true true false true
    public static void main(String[] args) {
        String hello = "Hello", lo = "lo";
        // Literal strings within the same class in the same package represent references to the same String object
        System.out.print((hello == "Hello") + " ");
        // Literal strings within different classes in the same package represent references to the same String object.
        System.out.print((Other.hello == hello) + " ");
        // Strings computed by constant expressions are computed at compile time and then treated as if they were literals.
        System.out.print((hello == ("Hel" + "lo")) + " ");
        // Strings computed by concatenation at run time are newly created and therefore distinct.
        System.out.print((hello == ("Hel" + lo)) + " ");
        // The result of explicitly interning a computed string is the same string as any pre-existing literal string with the same contents.
        System.out.println(hello == ("Hel" + lo).intern());
    }
}
