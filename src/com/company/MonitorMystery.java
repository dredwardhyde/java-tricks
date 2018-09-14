package com.company;

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("all")
public class MonitorMystery {
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
        } catch (Exception e) { throw new IllegalStateException("Could not initialize unsafe",e); }
    }

    public static void main(String... args) throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                getUnsafe().monitorEnter(MonitorMystery.class);
                try{
                    Thread.sleep(10_000);
                }catch (Exception e){ e.printStackTrace(); }
                getUnsafe().monitorExit(MonitorMystery.class);
            }
        };
        t.start();
        Thread.sleep(100);
        System.out.println("Trying to synchronize");
        System.out.println("Waiting...");
        synchronized (MonitorMystery.class) {
            System.out.println("Managed to synchronized");
        }
    }
}
