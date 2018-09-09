package com.company;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SignalTest {
    public static void main(String[] args) throws Exception {
        Signal signal = new Signal("INT");
        SignalHandler signalHandler = new SignalHandler() {
            @Override
            public void handle(Signal sig) {
                System.out.println(sig.getName() + " " + sig.getNumber());
            }
        };
        Signal.handle(signal, signalHandler);

        Thread.sleep(10_000_000);
    }
}
