package com.company;


public class Sleepy {

    {
        Thread.sleep(1000);
    }

    private Sleepy() throws InterruptedException{ }

    public static void main(String... args) throws InterruptedException{
        new Sleepy();
    }
}
