package com.company;

public class OverriddenSetterFromConstructor extends BaseClass {

    private int j = 3;

    /*
        Prints

        BaseClass constructor
        B i = 0 j = 0
        A i = 0
        0
     */
    public static void main(String[] args) {

        OverriddenSetterFromConstructor b = new OverriddenSetterFromConstructor();

        System.out.println(b.getI());
    }

    @Override
    public void setI() {
        i = j; // here j = 0 because OverriddenSetterFromConstructor constructor was not invoked yet
        System.out.println("B i = " + i + " j = " + j); // i = j = 0
    }
}

class BaseClass {

    protected int i = 1;

    BaseClass() {
        System.out.println("BaseClass constructor");
        setI(); // call overridden method from OverriddenSetterFromConstructor
        System.out.println("A i = " + i); // i = 0
    }

    public void setI() {
        i = 2;
    }

    public int getI() {
        return i;
    }
}
