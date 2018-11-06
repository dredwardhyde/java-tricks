package com.company;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TSXTest {
    final int N;
    final Object o = new Object();
    final int[] variables;
    ReentrantLock rl = new ReentrantLock(false);
    public TSXTest(int n) {
        this.N = n;
        variables = new int[N];
    }

    /*  Microsoft Windows [Version 10.0.17134.376]

	    Intel(R) Core(TM) i7-7700 CPU @ 3.60GHz

        java version "1.8.0_181"
        Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
        Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)

        -server -XX:+UseRTMLocking

        From docs: RTM improves performance for highly contended locks with low conflict in a critical
        region (which is code that must not be accessed by more than one thread concurrently).

        synchronized: true; Nmb of Variables:100000;  Duration:3568
        synchronized: true; Nmb of Variables:10000;  Duration:4248
        synchronized: true; Nmb of Variables:1000;  Duration:5913
        synchronized: true; Nmb of Variables:100;  Duration:14290
        synchronized: true; Nmb of Variables:10;  Duration:28125
        synchronized: true; Nmb of Variables:1;  Duration:34219


        Synchronized blocks in Java are reentrant. This means, that if a Java thread enters a synchronized block of code,
        and thereby take the lock on the monitor object the block is synchronized on, the thread
        can enter other Java code blocks synchronized on the same monitor object.

        -server
        -XX:+UseBiasedLocking
        -XX:BiasedLockingStartupDelay=1
        -XX:BiasedLockingBulkRebiasThreshold=1
        -XX:BiasedLockingBulkRevokeThreshold=100
        -XX:BiasedLockingDecayTime=100

        synchronized: true; Nmb of Variables:100000;  Duration:23142
        synchronized: true; Nmb of Variables:10000;  Duration:16949
        synchronized: true; Nmb of Variables:1000;  Duration:14102
        synchronized: true; Nmb of Variables:100;  Duration:12279
        synchronized: true; Nmb of Variables:10;  Duration:12021
        synchronized: true; Nmb of Variables:1;  Duration:11680


        macOS 10.13.6 (17G65)
        MacBook Pro 2017 Intel(R) Core(TM) i7-7820HQ CPU @ 2.90GHz

        -server
        -XX:+UseBiasedLocking
        -XX:BiasedLockingStartupDelay=1
        -XX:BiasedLockingBulkRebiasThreshold=1
        -XX:BiasedLockingBulkRevokeThreshold=100
        -XX:BiasedLockingDecayTime=100

        synchronized: true; Nmb of Variables:100000;  Duration:28831
        synchronized: true; Nmb of Variables:10000;  Duration:15500
        synchronized: true; Nmb of Variables:1000;  Duration:12661
        synchronized: true; Nmb of Variables:100;  Duration:12209
        synchronized: true; Nmb of Variables:10;  Duration:12075
        synchronized: true; Nmb of Variables:1;  Duration:12158



        -server -XX:+UseRTMLocking

        synchronized: true; Nmb of Variables:100000;  Duration:3870
        synchronized: true; Nmb of Variables:10000;  Duration:4579
        synchronized: true; Nmb of Variables:1000;  Duration:8797
        synchronized: true; Nmb of Variables:100;  Duration:17303
        synchronized: true; Nmb of Variables:10;  Duration:44750
        synchronized: true; Nmb of Variables:1;  Duration:34312



        -server

        ReentrantLock rl = new ReentrantLock(false);

        synchronized: true; Nmb of Variables:100000;  Duration:22417
        synchronized: true; Nmb of Variables:10000;  Duration:23962
        synchronized: true; Nmb of Variables:1000;  Duration:13579
        synchronized: true; Nmb of Variables:100;  Duration:12815
        synchronized: true; Nmb of Variables:10;  Duration:12793
        synchronized: true; Nmb of Variables:1;  Duration:11131


     */
    public static void main(String[] args) throws InterruptedException {
        int n = 100000;
        while (true) {
            new TSXTest(n).go(false);
            if (n == 1) {
                break;
            }
            n = n / 10;
        }

        n = 100000;
        while (true) {
            new TSXTest(n).go(true);
            if (n == 1) {
                break;
            }
            n = n / 10;
        }
    }

    private void go(boolean doSync) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4);
        Collection cs = new ArrayList<>();
        cs.add(new Task(100, doSync));
        cs.add(new Task(200, doSync));
        cs.add(new Task(300, doSync));
        cs.add(new Task(400, doSync));

        long start = System.nanoTime();
        es.invokeAll(cs);
        es.shutdown();
        System.out.println("synchronized: "+ doSync + "; Nmb of Variables:" + N + ";  Duration:" + ((System.nanoTime() - start) / 1000000));

    }

    public class Task implements Callable<Void> {

        final Random r;
        private int seed;
        private boolean sync;
        private int[] rnd = new int[N];

        public Task(int seed, boolean sync) {
            r = new Random(seed);
            this.seed = seed;
            this.sync = sync;
            List<Integer> rndList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                rndList.add(i);
            }

            Collections.shuffle(rndList, r);
            for (int i = 0; i < N; i++) {
                rnd[i] = rndList.get(i).intValue();
            }
        }

        @Override
        public Void call() {
            for (int i = 0; i < 100000000; i++) {
//                if (sync) {
//                    rl.lock();
//                    try{
//                        variables[rnd[i % N]]++;
//                    } finally { rl.unlock(); }
//                }
//                else variables[rnd[i % N]]++;
                if (sync)
                    synchronized (o) {
                        variables[rnd[i % N]]++;
                    }
                else variables[rnd[i % N]]++;
            }
            return null;
        }
    }
}
