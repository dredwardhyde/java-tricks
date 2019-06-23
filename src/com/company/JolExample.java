package com.company;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

public class JolExample {
    public static void main(String[] args) {
        out.println(VM.current().details());
        out.println(ClassLayout.parseClass(String.class).toPrintable());
    }
}
