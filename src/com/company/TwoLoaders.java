package com.company;

import java.net.URL;
import java.net.URLClassLoader;

public class TwoLoaders {
    /*
    true
    false
    Exception in thread "main" java.lang.ClassCastException: com.company.TwoLoaders cannot be cast to com.company.TwoLoaders
	    at com.company.TwoLoaders.main(TwoLoaders.java:14)
     */
    public static void main(String... args) throws Exception{
        TwoLoaders tl1 = new TwoLoaders();
        System.out.println(TwoLoaders.class.getResource("..").getPath());
        ClassLoader cl = new URLClassLoader(new URL[] {TwoLoaders.class.getResource("../..")}, null);
        Object tl2 = cl.loadClass("com.company.TwoLoaders").newInstance();
        System.out.println(tl2.getClass().getName().equals(tl1.getClass().getName()));
        System.out.println(tl2 instanceof com.company.TwoLoaders);
        tl1 = (TwoLoaders)tl2;
    }
}
