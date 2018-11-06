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
