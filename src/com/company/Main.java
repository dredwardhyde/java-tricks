package com.company;


import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

class OtherClass {

    private final int lol;

    private OtherClass() {
        System.out.println("test");
        this.lol = 10;
        System.out.println(lol);
    }
}


class ClassWithExpensiveConstructor {

    private final int value;

    private ClassWithExpensiveConstructor() {
        value = doExpensiveLookup();
    }

    private int doExpensiveLookup() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }

    public int getValue() {
        return value;
    }
}

public class Main{
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
        unsafeConstructor.setAccessible(true);
        Unsafe unsafe = unsafeConstructor.newInstance();
        ClassWithExpensiveConstructor instance = (ClassWithExpensiveConstructor)
                unsafe.allocateInstance(ClassWithExpensiveConstructor.class);
        System.out.println("Unsafe allocation: " + instance.getValue());

        Constructor<ClassWithExpensiveConstructor> silentConstructor = ( Constructor<ClassWithExpensiveConstructor> )ReflectionFactory.getReflectionFactory()
                .newConstructorForSerialization(ClassWithExpensiveConstructor.class, Object.class.getConstructor());
        silentConstructor.setAccessible(true);
        instance = silentConstructor.newInstance();
        System.out.println("Serialization constructor: " + instance.getValue());

        silentConstructor = ( Constructor<ClassWithExpensiveConstructor> )ReflectionFactory.getReflectionFactory()
                .newConstructorForSerialization(ClassWithExpensiveConstructor.class,
                        OtherClass.class.getDeclaredConstructor());
        silentConstructor.setAccessible(true);
        instance = silentConstructor.newInstance();
        System.out.println(instance.getValue());
        System.out.println(instance.getClass());
        System.out.println(instance.getClass().getSuperclass());
    }
}
