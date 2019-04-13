package com.company;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("all")
public class ManualMonitor {
    public static Unsafe getUnsafe() {
        try {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    if (field.getType() == Unsafe.class) {
                        field.setAccessible(true);
                        return (Unsafe) field.get(null);
                    }
                }
            }
            throw new IllegalStateException("Unsafe field not found");
        } catch (Exception e) {
            throw new IllegalStateException("Could not initialize unsafe", e);
        }
    }

    public static void main(String... args) throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                getUnsafe().monitorEnter(ManualMonitor.class);
                try {
                    Thread.sleep(10_000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getUnsafe().monitorExit(ManualMonitor.class);
            }
        };
        t.start();
        Thread.sleep(100);
        System.out.println("Trying to synchronize");
        System.out.println("Waiting...");
        synchronized (ManualMonitor.class) {
            System.out.println("Managed to synchronized");
        }
    }
}
