package com.company;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TSXTest {
    final int N;
    final Object o = new Object();
    final int[] variables;
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

        synchronized: false; Nmb of Variables:100000;  Duration:779
        synchronized: false; Nmb of Variables:10000;  Duration:1102
        synchronized: false; Nmb of Variables:1000;  Duration:1580
        synchronized: false; Nmb of Variables:100;  Duration:2361
        synchronized: false; Nmb of Variables:10;  Duration:1884
        synchronized: false; Nmb of Variables:1;  Duration:490
        synchronized: true; Nmb of Variables:100000;  Duration:3568
        synchronized: true; Nmb of Variables:10000;  Duration:4248
        synchronized: true; Nmb of Variables:1000;  Duration:5913
        synchronized: true; Nmb of Variables:100;  Duration:14290
        synchronized: true; Nmb of Variables:10;  Duration:28125
        synchronized: true; Nmb of Variables:1;  Duration:34219


        -server
        -XX:+UseBiasedLocking
        -XX:BiasedLockingStartupDelay=1
        -XX:BiasedLockingBulkRebiasThreshold=1
        -XX:BiasedLockingBulkRevokeThreshold=100
        -XX:BiasedLockingDecayTime=100

        synchronized: false; Nmb of Variables:100000;  Duration:775
        synchronized: false; Nmb of Variables:10000;  Duration:1095
        synchronized: false; Nmb of Variables:1000;  Duration:1620
        synchronized: false; Nmb of Variables:100;  Duration:2440
        synchronized: false; Nmb of Variables:10;  Duration:1744
        synchronized: false; Nmb of Variables:1;  Duration:497
        synchronized: true; Nmb of Variables:100000;  Duration:23142
        synchronized: true; Nmb of Variables:10000;  Duration:16949
        synchronized: true; Nmb of Variables:1000;  Duration:14102
        synchronized: true; Nmb of Variables:100;  Duration:12279
        synchronized: true; Nmb of Variables:10;  Duration:12021
        synchronized: true; Nmb of Variables:1;  Duration:11680
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
        //  -XX:+UseRTMLocking
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
        public Void call() throws Exception {
            for (int i = 0; i < 100000000; i++) {
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
