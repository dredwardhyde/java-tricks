package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoConcurrentModification {

    public static void main(String... args){
        try {
            List<String> list = Arrays.asList("String 1", "String 2", "String 3");
            list.stream().forEach(x -> {
                if (x.equals("String 2")) {
                    list.remove(x);
                }
            });
        }catch (Exception e){
            /*
            java.lang.UnsupportedOperationException
                at java.util.AbstractList.remove(AbstractList.java:161)
                at java.util.AbstractList$Itr.remove(AbstractList.java:374)
                at java.util.AbstractCollection.remove(AbstractCollection.java:293)
                at com.company.NoConcurrentModification.lambda$main$0(NoConcurrentModification.java:14)
                at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
                at java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:580)
                at com.company.NoConcurrentModification.main(NoConcurrentModification.java:12)
             */
            e.printStackTrace();
        }

        try{
            List<String> list2 = new ArrayList<>(Arrays.asList("String 1", "String 2", "String 3"));
            list2.stream().forEach(x -> {
                if(x.equals("String 2")){
                    // really removes object, all objects are moved 1 index to the left, iterator().next() returns null
                    list2.remove(x);
                }
            });
        }catch (Exception e){
            /*
            java.lang.NullPointerException
                at com.company.NoConcurrentModification.lambda$main$1(NoConcurrentModification.java:24)
                at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1382)
                at java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:580)
                at com.company.NoConcurrentModification.main(NoConcurrentModification.java:23)
             */
            e.printStackTrace();
        }

        try{
            List<String> list2 = new ArrayList<>(Arrays.asList("String 1", "String 2", "String 3"));
            list2.forEach(x -> {
                if(x.equals("String 2")){
                    list2.remove(x);
                }
            });
        }catch (Exception e){
            /*
            java.util.ConcurrentModificationException
                at java.util.ArrayList.forEach(ArrayList.java:1260)
                at com.company.NoConcurrentModification.main(NoConcurrentModification.java:51)
             */
            e.printStackTrace();
        }
    }
}
