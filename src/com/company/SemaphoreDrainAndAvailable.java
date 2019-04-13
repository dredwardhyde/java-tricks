package com.company;

import java.util.concurrent.Semaphore;

public class SemaphoreDrainAndAvailable {
    // https://bugs.openjdk.java.net/browse/JDK-8169272
    public static void main(String... args){
        Semaphore semaphore = new Semaphore(-42);
        semaphore.drainPermits(); // changes value to 0
        System.out.println(semaphore.availablePermits()); // prints 0
    }
}
