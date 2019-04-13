package com.company;

/*
    The identity hashcode is computed by the JVM at object creation and serves
    - amongst others - as fallback for an object's hashcode value.
    That is Object.hashcode() will return the identity hashcode of your object.
    This value will not change during the lifetime of your object.
 */
public class IdentityHashCode {


    public static void main(String... args) {
        Integer x = 100;
        Integer y = 100;
        Integer z = Integer.parseInt("100");
        System.out.println(System.identityHashCode(x) == System.identityHashCode(y));
        System.out.println(x.hashCode() == y.hashCode());
        System.out.println(x.hashCode() == z.hashCode());

        System.out.println();

        Integer i1 = new Integer(1000000);
        Integer i2 = new Integer(1000000);
        System.out.println("I1 identity: " + System.identityHashCode(i1));
        System.out.println("I2 identity: " + System.identityHashCode(i2));
        System.out.println(System.identityHashCode(i1) != System.identityHashCode(i2));
        System.out.println(i1.hashCode() != i2.hashCode());

        System.out.println("Hashcode was not overloaded:");
        IdentityHashCode identityHashCode = new IdentityHashCode();
        identityHashCode.hashCode();
        System.out.println(System.identityHashCode(identityHashCode) == identityHashCode.hashCode());
    }
}
