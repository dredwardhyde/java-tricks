package com.company;


import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
class LargeObjectFinalizer extends PhantomReference<Object> {
    private Class type;
    public LargeObjectFinalizer(Object referent, ReferenceQueue<? super Object> q) {
        super(referent, q);
        this.type = referent.getClass();
    }
    public void finalizeResources() {
        System.out.println("clearing in thread: "+ type.getName() + " " + Thread.currentThread().getName());
    }
}

class A {
    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("A finalize " + this + " in thread " + Thread.currentThread().getName());
        } finally {
            super.finalize();
        }
    }
}

class B { }
/*
    If the garbage collector determines at a certain point in time that the referent of a phantom reference
    is phantom reachable, then at that time or at some later time it will enqueue the reference.

    An object is phantom reachable if it is neither strongly, softly, nor weakly reachable,
    it has been finalized, and some phantom reference refers to it.
 */
public class PhantomReferenceExample {

    /*
        First gc cycle - finalize methods were invoked on objects A
        A finalize com.company.A@6a57f431 in thread Finalizer
        A finalize com.company.A@6490070b in thread Finalizer
        A finalize com.company.A@791fdf34 in thread Secondary finalizer
        A finalize com.company.A@60af9f41 in thread Finalizer
        A finalize com.company.A@77d83e67 in thread Secondary finalizer
        A finalize com.company.A@25ad0d30 in thread Finalizer
        A finalize com.company.A@73e7b444 in thread Secondary finalizer
        A finalize com.company.A@708e93c8 in thread Finalizer
        A finalize com.company.A@36e5345c in thread Secondary finalizer
        A finalize com.company.A@14426a8d in thread Finalizer
        Second gc cycle - PhantomReference objects A are enqueued
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        clearing in thread: com.company.A main
        Third gc cycle - PhantomReference objects B are enqueued
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
        clearing in thread: com.company.B main
     */
    public static void main(String... args){
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        List<LargeObjectFinalizer> references = new ArrayList<>();

        List<A> largeObjectsA = new ArrayList<>();
        List<B> largeObjectsB = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            A a = new A();
            B b = new B();
            largeObjectsA.add(a);
            largeObjectsB.add(b);
            references.add(new LargeObjectFinalizer(a, referenceQueue));
            references.add(new LargeObjectFinalizer(b, referenceQueue));
        }

        largeObjectsA = null; // free objects A

        // Run first GC and force calling finalize() on all freed objects
        // some of them in Finalizer thread and some - in Secondary finalizer
        System.out.println("First gc cycle - finalize methods were invoked on objects A");
        System.gc();
        System.runFinalization();
        System.out.println("Second gc cycle - PhantomReference objects A are enqueued");
        System.gc();

        Reference<?> referenceFromQueue;
        while ((referenceFromQueue = referenceQueue.poll()) != null) {
            ((LargeObjectFinalizer)referenceFromQueue).finalizeResources();
            referenceFromQueue.clear();
        }

        largeObjectsB = null; // free objects B
        System.gc(); // Objects B don't override finalize() so they are expected to be collected during their first cycle

        System.out.println("Third gc cycle - PhantomReference objects B are enqueued");

        while ((referenceFromQueue = referenceQueue.poll()) != null) {
            ((LargeObjectFinalizer)referenceFromQueue).finalizeResources();
            referenceFromQueue.clear();
        }
    }
}
