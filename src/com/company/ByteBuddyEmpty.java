package com.company;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.StubMethod;

import java.lang.management.ManagementFactory;

import static net.bytebuddy.matcher.ElementMatchers.any;

interface Testable {
    String getTest();
    void setTest(String test);
    void invokeTest();
}

public class ByteBuddyEmpty {

    public static void main(String args[]) throws Exception{
        int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

        System.out.println("PID: " + pid);

        Class stubClass = new ByteBuddy()
                //Добавляем интерфейс
                .subclass(Testable.class)
                //На все вызовы интерфейса + стандартных методов Object
                .method(any())
                //Возвращаются дефолтные значения для данного типа из созданного прокси синглтона
                .intercept(StubMethod.INSTANCE)
                //Созданный класс инжектится в существующий classloader (по умолчанию добавляется в собственный
                //classloader ByteBuddy
                .make().load(ByteBuddyEmpty.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        System.out.println(stubClass.getName());

        Testable stub = (Testable)stubClass.newInstance();

        stub.setTest("lol");
        stub.invokeTest();
        System.out.println(stub.getTest());
        System.out.println(stub.toString());
        System.out.println(stub.hashCode());

        Thread.sleep(10_000_000);
    }
}
