package com.company;

import java.util.Vector;


public class BiasLocking {

    private static final int COUNT = 5_000_000;


    public static void main(String[] args) throws Exception {

        long n1 = System.nanoTime();
        final Vector<Integer> stringBuffer = new Vector<Integer>(15_000_000);

        for (int i = 0; i < COUNT; i++) {
                stringBuffer.add(1);
        }
        long n2 = System.nanoTime();
        System.out.println("##################################" + (n2 - n1) + " Result main: " + stringBuffer.size() + " 1 op = " + (n2 - n1) / COUNT);



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long n1 = System.nanoTime();
                for (int i = 0; i < COUNT; i++) {
                    stringBuffer.add(2);
                }
                long n2 = System.nanoTime();
                System.out.println("##################################" + (n2 - n1) + " Result work: " + stringBuffer.size() + " 1 op = " + (n2 - n1) / COUNT);

                try{
                    Thread.sleep(22_000);
                }catch (Exception e){
                    e.printStackTrace();
                }


                n1 = System.nanoTime();
                for (int i = 0; i < COUNT; i++) {
                    stringBuffer.add(2);
                }
                n2 = System.nanoTime();
                System.out.println("##################################" + (n2 - n1) + " Result work: " + stringBuffer.size() + " 1 op = " + (n2 - n1) / COUNT);
            }
        });
        thread.start();

        Thread.sleep(5000);

        n1 = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            stringBuffer.add(1);
        }
        n2 = System.nanoTime();
        System.out.println("##################################" + (n2 - n1) + " Result main: " + stringBuffer.size() + " 1 op = " + (n2 - n1) / COUNT);


    }
}
