# Some useful & interesting Java snippets

1.  [Tested performance of two nested synchronized blocks with basic locking, RTM locking and no locking at all](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/Accounts.java)  
    [Output of jstack -l <pid> when deadlock occurred](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/accounts_deadlock.txt)
    
2.  [What will happen if we use Arrays.setAll(new List[2], ArrayList::new)](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ArraysSetAll.java)

3.  [How to tune BiasedLocking?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/BiasLocking.java)

4.  [Bitwise operations examples](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/BitwiseOperations.java)

5.  [How to create empty interface implementation at runtime using ByteBuddy?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ByteBuddyEmpty.java)

6.  [Streams & XChart example](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/CharCount.java)

7.  [What will happen if we cast negative integer to char?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/CharOverflow.java)

8.  [Do you know there is unary plus operator in Java?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/CharacterToString.java)

9.  [Optional explicit 'this' as first parameter in instance methods definitions since Java 8](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ComparableThis.java)

10. [Various behavior of multiple ways of finding max element in collection with null element](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ComparatorsWithNull.java)

11. [Type inference in conditional expressions](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ConditionalExpressionNPE.java)

12. [Classic one producer/one consumer and multiple consumers/multiple producers examples](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ConsumerProducer.java)

13. [Basic example how to use C3PO jdbc connection pool](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/DSTest.java)

14. [You can implement interfaces in enums, but not Cloneable and Comparable](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/EnumsWithInterfaces.java)

15. [You can 'return' Throwable instance from method without throwing it](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ExceptionPreparing.java)

16. [Completely different behavior of forEachRemaining in different collections](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ForEachRemainingExample.java)

17. [Inheriting Methods with Override-Equivalent Signatures](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/FunctionalInterfaceExample.java)

18. [Lambda can not implement generic method, but method reference can](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/FunctionalInterfacesPartTwo.java)

19. [Explained various examples with Generics](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/GenericsExplained.java)

20. [When you should use System.identityHashCode() ?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/IdentityHashCode.java)

21. [What will happen if we do narrowing cast of Infinity & Max numeric values?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/InfinityCasting.java)
 
22. [There is Long & Integer cache in Java](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/IntegerCacheExample.java) 

23. [How to invoke local class instance from different method?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/InvokeLocalClass.java)

24. [How to use Java Object Layout?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/JolExample.java)

25. [How to easily implement LRU cache in Java?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/LRUCache.java)

26. [How to use ReflectionFactory.getReflectionFactory().newConstructorForSerialization()?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/Main.java)
                
27. [Manual object lock using Unsafe](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ManualMonitor.java)

28. [How to swap content of two Maps in one method call?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/MapSwap.java)

29. [Off-heap memory allocation using Unsafe](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/Memory.java)

30. [Lambda vs method reference differences at runtime](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/MethodReferenceVsLambda.java)

31. [How to implement multithreaded throttling?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/MultiThreadMap.java)

32. [Different behavior of various collection implementations during concurrent modification](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/NoConcurrentModification.java)

33. [Some details related to object instantiation order](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/OverriddenSetterFromConstructor.java)

34. [How to use Phantom references?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/PhantomReferenceExample.java)

35. [Private methods, fields and inheritance](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/PrivateClassInheritance.java)

36. [Returning value from 'finally' block](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ReturnFromFinally.java)

37. [Semaphore.drainPermits() bug](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/SemaphoreDrainAndAvailable.java)

38. [Shadowing variables](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ShadowTest.java)

39. [How to intercept signals to Java process?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/SignalTest.java)

40. [How to measure object's size at runtime?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/SizeOf.java)

41. [How to use Soft and Weak references?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/SoftAndWeakReferenceExample.java)

42. [Late binding in Streams](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/StreamLateBinding.java)

43. [Stream.of(-3, -2, -1, 0, 1, 2, 3).max(Math::max)](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/StreamMax.java)

44. [String literals equality](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/StringLiterals.java)

45. [How RTMLocking increase 'synchronized' performance?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/TSXTest.java)

46. [BigDecimal with and without strictfp](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/Test.java)

47. [Various class modifiers (including local classes)](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/TestClassModifiers.java)

48. [How to truncate String to N bytes?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/TruncateStringsToBytes.java)

49. [Class equality with different classloaders](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/TwoLoaders.java)

50. [Type inference bug example (and there are a lot of them in Java)](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/TypeInferenceError.java)

51. [Why upper casting with shifts is dangerous?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/UpperCastingShiftProblem.java)

52. [Why you can't use ConcurrentSkipListMap as expiration queue?  Because System.currentTimestamp() accuracy is low.  Here is how to make multithreaded expiration queue.](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/WaitForExpiration.java)

53. [How to use class instance creation expression and how it works?](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/ClassInstanceCreation.java)

54. [FizzBuzz example using Java Streams & Eclipse Collections](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/FizzBuzz.java)

55. [Exceptions in instance initialization blocks and constructor's signature](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/Sleepy.java)

56. [Initialization order of final primitive fields](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/OrderPuzzle.java)

57. [Try/Catch Monad in Java](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/MonadsTryCatchExample.java)
    
58. [Runtime Java code compilation and .jar assembly](https://github.com/dredwardhyde/java-tricks/blob/master/src/com/company/RuntimeCompTest.java)