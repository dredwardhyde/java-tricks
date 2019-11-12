package com.company;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TSXTest {
    private final int N;
    private final Object o = new Object();
    private final int[] variables;

    private TSXTest(int n) {
        this.N = n;
        variables = new int[N];
    }

    /*
        macOS 10.14.6 (18G1012)
        MacBook Pro 2019 Intel(R) Core(TM) i9-9880h CPU @ 2.30GHz

        openjdk version "11.0.4" 2019-07-16 LTS
        OpenJDK Runtime Environment Zulu11.33+15-CA (build 11.0.4+11-LTS)
        OpenJDK 64-Bit Server VM Zulu11.33+15-CA (build 11.0.4+11-LTS, mixed mode)

        -server
        -XX:+UseBiasedLocking
        -XX:BiasedLockingStartupDelay=1
        -XX:BiasedLockingBulkRebiasThreshold=1
        -XX:BiasedLockingBulkRevokeThreshold=100
        -XX:BiasedLockingDecayTime=100

        synchronized: true; Nmb of Variables:100000;  Duration:27144
        synchronized: true; Nmb of Variables:10000;  Duration:24012
        synchronized: true; Nmb of Variables:1000;  Duration:19395
        synchronized: true; Nmb of Variables:100;  Duration:13515
        synchronized: true; Nmb of Variables:10;  Duration:16933
        synchronized: true; Nmb of Variables:1;  Duration:17283


        -server -XX:+UseRTMLocking

        synchronized: true; Nmb of Variables:100000;  Duration:3799
        synchronized: true; Nmb of Variables:10000;  Duration:3969
        synchronized: true; Nmb of Variables:1000;  Duration:7173
        synchronized: true; Nmb of Variables:100;  Duration:19574
        synchronized: true; Nmb of Variables:10;  Duration:35347
        synchronized: true; Nmb of Variables:1;  Duration:39398


        -server

        synchronized: true; Nmb of Variables:100000;  Duration:25696
        synchronized: true; Nmb of Variables:10000;  Duration:20179
        synchronized: true; Nmb of Variables:1000;  Duration:17612
        synchronized: true; Nmb of Variables:100;  Duration:16435
        synchronized: true; Nmb of Variables:10;  Duration:15290
        synchronized: true; Nmb of Variables:1;  Duration:17787



     */
    public static void main(String[] args) throws InterruptedException {
        int n = 100000;
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
        List<Task> cs = new ArrayList<>();
        cs.add(new Task(100, doSync));
        cs.add(new Task(200, doSync));
        cs.add(new Task(300, doSync));
        cs.add(new Task(400, doSync));

        long start = System.nanoTime();
        es.invokeAll(cs);
        es.shutdown();
        System.out.println("synchronized: " + doSync + "; Nmb of Variables:" + N + ";  Duration:" + ((System.nanoTime() - start) / 1000000));

    }

    public class Task implements Callable<Void> {

        final Random r;
        private boolean sync;
        private int[] rnd = new int[N];

        public Task(int seed, boolean sync) {
            r = new Random(seed);
            this.sync = sync;
            List<Integer> rndList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                rndList.add(i);
            }

            Collections.shuffle(rndList, r);
            for (int i = 0; i < N; i++) {
                rnd[i] = rndList.get(i);
            }
        }

        @Override
        public Void call() {
            for (int i = 0; i < 100000000; i++) {
                if (sync)
                    synchronized (o) {
                        variables[rnd[i % N]]++;
                    }
                else
                    variables[rnd[i % N]]++;
            }
            return null;
        }
    }
}
