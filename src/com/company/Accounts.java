package com.company;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Account {
    private String id = UUID.randomUUID().toString();
    private long money = 1_000_000_000;

    String getId() {
        return id;
    }

    long getMoney() {
        return money;
    }

    void setMoney(long money) {
        this.money = money;
    }
}

public class Accounts {

    private static final List<Account> accounts = new ArrayList<>();

    /*
    -server

    Nmb of Variables:100000;  Duration:13003
    Nmb of Variables:10000;  Duration:3934
    Nmb of Variables:1000;  Duration:3768
    Nmb of Variables:100;  Duration:5315
    Nmb of Variables:10;  Duration:13387
    Nmb of Variables:1;  Duration:36190



    -server -XX:+UseRTMLocking

    Nmb of Variables:100000;  Duration:12827
    Nmb of Variables:10000;  Duration:3846
    Nmb of Variables:1000;  Duration:3323
    Nmb of Variables:100;  Duration:5028
    Nmb of Variables:10;  Duration:19678
    Nmb of Variables:1;  Duration:1958824



    no synchronization at all

    Nmb of Variables:100000;  Duration:7122
    Nmb of Variables:10000;  Duration:1727
    Nmb of Variables:1000;  Duration:1449
    Nmb of Variables:100;  Duration:2003
    Nmb of Variables:10;  Duration:3424
    Nmb of Variables:1;  Duration:3629
     */
    public static void main(String... args) {
        for (int i = 0; i < 100000; i++) {
            accounts.add(new Account());
        }

        int n = 100000;
        while (true) {
            new Accounts().go(n);
            if (n == 1) {
                break;
            }
            n = n / 10;
        }
    }

    private static void transferMoney(Account from, long amount, Account to) {

        //sort by id to avoid deadlocks, see accounts_deadlock.txt for example

        if (from.getId().compareTo(to.getId()) < 0) {
            synchronized (from) {
                synchronized (to) {
                    long before = to.getMoney() - from.getMoney();
                    from.setMoney(from.getMoney() - amount);
                    to.setMoney(to.getMoney() + amount);
                    long after = to.getMoney() - from.getMoney();
                    assert ((after - before) == 20 || (after - before) == 0) : "NO SYNCHRONIZATION";
                }
            }
        } else {
            synchronized (to) {
                synchronized (from) {
                    long before = to.getMoney() - from.getMoney();
                    from.setMoney(from.getMoney() - amount);
                    to.setMoney(to.getMoney() + amount);
                    long after = to.getMoney() - from.getMoney();
                    assert ((after - before) == 20 || (after - before) == 0) : "NO SYNCHRONIZATION";
                }
            }
        }
    }

    private void go(int n) {
        try {
            ExecutorService es = Executors.newFixedThreadPool(4);
            Collection<Task> cs = new ArrayList<>();
            cs.add(new Task(100, n));
            cs.add(new Task(200, n));
            cs.add(new Task(300, n));
            cs.add(new Task(400, n));

            long start = System.nanoTime();
            es.invokeAll(cs);
            es.shutdown();
            System.out.println("Nmb of Variables:" + n + ";  Duration:" + ((System.nanoTime() - start) / 1000000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Task implements Callable<Void> {

        final Random r;
        private int[] rnd;
        private int n;

        public Task(int seed, int N) {
            r = new Random(seed);
            rnd = new int[N];
            n = N;
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
            try {
                for (int i = 0; i < 50000000; i++) {
                    int first = rnd[i % n];
                    int second = rnd[(50000000 - i) % n];
                    transferMoney(accounts.get(first), 10, accounts.get(second));
                }
                // Throwable to catch AssertionError
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
