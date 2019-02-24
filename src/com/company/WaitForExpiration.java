package com.company;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class WaitForExpiration {
    public static void main(String... args) throws Exception{
        int numberOfThreads = 6;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        //Fast queue - adding elements from multiple threads in lock-free manner
        final ConcurrentLinkedQueue<Instant> events = new ConcurrentLinkedQueue<>();

        //Slow queue - moving elements from events here in single thread, then consuming them also in one thread after slow locking filter
        final LinkedBlockingQueue<Instant> eventsToConsume = new LinkedBlockingQueue<>();

        //Adding elements to fast queue in multiple threads
        Runnable producer = () -> IntStream.rangeClosed(5, 10).forEach(index -> {
            events.add(Instant.now().plusSeconds(index));
        });
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        //Moving elements from fast queue to slow queue  by single thread
        Thread retainer = new Thread(() -> {
            while(true){
                Instant instant = events.poll();
                if(instant != null) try{ eventsToConsume.put(instant); }catch (InterruptedException e){e.printStackTrace();}
            }
        });
        retainer.start();

        //Querying slow queue and consuming elements that already expired
        Thread expirerer = new Thread(() -> {
            while(true){
                eventsToConsume.stream().filter(x -> x.toEpochMilli() < System.currentTimeMillis()).forEach((Instant key)->{
                    System.out.println("Current time: " + Instant.now() + " key: " + key);
                    eventsToConsume.remove(key);
                });
            }
        });
        expirerer.start();
        expirerer.join();
        retainer.join();
    }
}
