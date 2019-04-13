package com.company;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Produced 0
    Consume 0 Thread-3
    Consume 0 Thread-2
    Produced 0
    Produced 1
    Consume 1 Thread-3
    Consume 1 Thread-2
    Produced 1
    Produced 2
    Consume 2 Thread-3
    Produced 3
    ---------------------------------------------------------------------------------------------
    <<hangs here>> see comments in code
    ---------------------------------------------------------------------------------------------

    2019-03-29 19:31:08
    Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.181-b13 mixed mode):

    "Attach Listener" #15 daemon prio=9 os_prio=31 tid=0x00007faa3b8f3800 nid=0xa603 runnable [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "Thread-3" #14 prio=5 os_prio=31 tid=0x00007faa3a086000 nid=0x5b03 in Object.wait() [0x000070000cd5c000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac8c0b0> (a com.company.Buffer)
        at java.lang.Object.wait(Object.java:502)
        at com.company.Buffer.poll(ConsumerProducer.java:41)
        - locked <0x000000076ac8c0b0> (a com.company.Buffer)
        at com.company.ConsumerProducer.lambda$main$1(ConsumerProducer.java:73)
        at com.company.ConsumerProducer$$Lambda$2/960604060.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

    "Thread-2" #13 prio=5 os_prio=31 tid=0x00007faa3a085000 nid=0x5903 in Object.wait() [0x000070000cc59000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac8c0b0> (a com.company.Buffer)
        at java.lang.Object.wait(Object.java:502)
        at com.company.Buffer.poll(ConsumerProducer.java:41)
        - locked <0x000000076ac8c0b0> (a com.company.Buffer)
        at com.company.ConsumerProducer.lambda$main$1(ConsumerProducer.java:73)
        at com.company.ConsumerProducer$$Lambda$2/960604060.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

    "Thread-1" #12 prio=5 os_prio=31 tid=0x00007faa3a084800 nid=0x5803 in Object.wait() [0x000070000cb56000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac8c0b0> (a com.company.Buffer)
        at java.lang.Object.wait(Object.java:502)
        at com.company.Buffer.add(ConsumerProducer.java:25)
        - locked <0x000000076ac8c0b0> (a com.company.Buffer)
        at com.company.ConsumerProducer.lambda$main$0(ConsumerProducer.java:61)
        at com.company.ConsumerProducer$$Lambda$1/122883338.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

    "Thread-0" #11 prio=5 os_prio=31 tid=0x00007faa3a080800 nid=0x5703 in Object.wait() [0x000070000ca53000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac8c0b0> (a com.company.Buffer)
        at java.lang.Object.wait(Object.java:502)
        at com.company.Buffer.add(ConsumerProducer.java:25)
        - locked <0x000000076ac8c0b0> (a com.company.Buffer)
        at com.company.ConsumerProducer.lambda$main$0(ConsumerProducer.java:61)
        at com.company.ConsumerProducer$$Lambda$1/122883338.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

    "Service Thread" #10 daemon prio=9 os_prio=31 tid=0x00007faa3c089000 nid=0x3e03 runnable [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "C1 CompilerThread3" #9 daemon prio=9 os_prio=31 tid=0x00007faa3c07c000 nid=0x4003 waiting on condition [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "C2 CompilerThread2" #8 daemon prio=9 os_prio=31 tid=0x00007faa3c07b800 nid=0x3c03 waiting on condition [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "C2 CompilerThread1" #7 daemon prio=9 os_prio=31 tid=0x00007faa3c07a800 nid=0x4303 waiting on condition [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "C2 CompilerThread0" #6 daemon prio=9 os_prio=31 tid=0x00007faa3c01c800 nid=0x3a03 waiting on condition [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "Monitor Ctrl-Break" #5 daemon prio=5 os_prio=31 tid=0x00007faa3a080000 nid=0x3903 runnable [0x000070000c33e000]
       java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x000000076adcc408> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x000000076adcc408> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

    "Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007faa3b823800 nid=0x3703 waiting on condition [0x0000000000000000]
       java.lang.Thread.State: RUNNABLE

    "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007faa3a818000 nid=0x3103 in Object.wait() [0x000070000c138000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
        - locked <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

    "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007faa3a811800 nid=0x3003 in Object.wait() [0x000070000c035000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

    "main" #1 prio=5 os_prio=31 tid=0x00007faa3b801800 nid=0x2603 in Object.wait() [0x000070000b617000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b0f0998> (a java.lang.Thread)
        at java.lang.Thread.join(Thread.java:1252)
        - locked <0x000000076b0f0998> (a java.lang.Thread)
        at java.lang.Thread.join(Thread.java:1326)
        at com.company.ConsumerProducer.main(ConsumerProducer.java:92)

    "VM Thread" os_prio=31 tid=0x00007faa3a80d000 nid=0x4c03 runnable

    "GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007faa3c800800 nid=0x1f07 runnable

    "GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007faa3a801800 nid=0x2a03 runnable

    "GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007faa3a802000 nid=0x5403 runnable

    "GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007faa3a803000 nid=0x5303 runnable

    "GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007faa3a008800 nid=0x5103 runnable

    "GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007faa3a009000 nid=0x4f03 runnable

    "GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007faa3a803800 nid=0x2d03 runnable

    "GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007faa3a804000 nid=0x4d03 runnable

    "VM Periodic Task Thread" os_prio=31 tid=0x00007faa3c089800 nid=0x5503 waiting on condition

    JNI global references: 319

    Heap
     PSYoungGen      total 76288K, used 14425K [0x000000076ab00000, 0x0000000770000000, 0x00000007c0000000)
      eden space 65536K, 22% used [0x000000076ab00000,0x000000076b916430,0x000000076eb00000)
      from space 10752K, 0% used [0x000000076f580000,0x000000076f580000,0x0000000770000000)
      to   space 10752K, 0% used [0x000000076eb00000,0x000000076eb00000,0x000000076f580000)
     ParOldGen       total 175104K, used 0K [0x00000006c0000000, 0x00000006cab00000, 0x000000076ab00000)
      object space 175104K, 0% used [0x00000006c0000000,0x00000006c0000000,0x00000006cab00000)
     Metaspace       used 4728K, capacity 4820K, committed 4992K, reserved 1056768K
      class space    used 528K, capacity 564K, committed 640K, reserved 1048576K


    Process finished with exit code 130 (interrupted by signal 2: SIGINT)

 */

// Solution from https://dzone.com/articles/the-evolution-of-producer-consumer-problem-in-java
// not suitable for multiple consumers and producers
// for one consumer and one producer
// while (list.size() >= size) {
//      wait();
// }
// while (list.size() == 0) {
//      wait();
// }
// could be replaced with IFs with same conditions


class Buffer {
    private Queue<Integer> list;

    private int size;

    public Buffer(int size) {
        // non-threadsafe container
        this.list = new LinkedList<>();
        // our target size
        this.size = size;
    }

    public void add(int value) throws InterruptedException {
        // synchronized on "this" because we use one "list" we need to protect per Buffer object
        synchronized (this) {
            // prevent producer from adding to "queue" more "values" than "size"
            // "while" - but not if prevents from notify from other producers - not consumers
            // "if" - will work with ONE consumer and ONE producer
            while (list.size() >= size) {
                wait(); // producer and consumer could hang together if one producer wakes another with notify() when queue is full
            }
            // adding "size" value to "queue"
            list.add(value);
            // we could use notifyAll(),
            // but there will be only one thread that's going to take value,
            // other threads will be locked on list.size() == 0
            notifyAll(); // notify() wakes any arbitrary thread which is waiting on wait() - it could be consumer OR producer.
            // It will hang all threads if it wakes producer and queue is full.
            // notifyAll() will wake all threads - producers will be able to add objects to queue if it's not full, and will wait() otherwise,
            // consumers will be able to read values from queue
        }
    }

    public int poll() throws InterruptedException {
        synchronized (this) {
            // only size threads could take values from queue
            // "while" - but not if - prevents from catching "notify" intended from other "consumer"
            // "if" - will work with ONE consumer and ONE producer
            while (list.size() == 0) {
                wait(); // producer and consumer could hang together if one producer wakes another with notify() when queue is full
            }
            // with "if" and multiple consumers we could get NPE in next line
            int value = list.poll();
            notifyAll(); // same situation as with notifyAll() in add()
            return value;
        }
    }
}


class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private Queue<Integer> list;
    private int size;

    public BoundedBuffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }

    public void add(int value) throws InterruptedException {
        lock.lock();
        try {
            while (list.size() >= size)
                condition.await();
            list.add(value);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int poll() throws InterruptedException {
        lock.lock();
        try {
            while (list.size() == 0)
                condition.await();
            int value = list.poll();
            condition.signalAll();
            return value;
        } finally {
            lock.unlock();
        }
    }
}

public class ConsumerProducer {

    public static void main(String... args) {
        try {
            BoundedBuffer buffer = new BoundedBuffer(10);
            Runnable producer = () -> {
                try {
                    int value = 0;
                    while (true) {
                        buffer.add(value);
                        System.out.println("Produced " + value);
                        value++;
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Runnable consumer = () -> {
                try {
                    while (true) {
                        int value = buffer.poll();
                        System.out.println("Consume " + value + " " + Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread producerThread1 = new Thread(producer);
            producerThread1.start();

            Thread.sleep(2);
            Thread producerThread2 = new Thread(producer);
            producerThread2.start();

            Thread consumerThread1 = new Thread(consumer);
            consumerThread1.start();

            Thread consumerThread2 = new Thread(consumer);
            consumerThread2.start();

            producerThread1.join();
            producerThread2.join();

            consumerThread1.join();
            consumerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
