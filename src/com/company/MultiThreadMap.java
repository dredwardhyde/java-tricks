package com.company;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadMap {
    private static long N = 10l;
    private static long T = 10l;
    private static final Cache<String, AtomicLong> cache = CacheBuilder.newBuilder().expireAfterWrite(T, TimeUnit.SECONDS).build();

    private static boolean isPossibleToDo(String key) {
        try {
            AtomicLong oldValue = cache.get(key, () -> new AtomicLong(0));
            synchronized (oldValue) {
                if (!(oldValue.get() == N)) {
                    System.out.println(Thread.currentThread().getName() + " " + oldValue.incrementAndGet() + " " + key);
                    return true;
                } else
                    return false;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String... args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 8; i++) {
            threadPool.submit(() -> {
                while (true) {
                    isPossibleToDo("lol");
                    isPossibleToDo("lol1");
                }
            });
        }
    }
}
