package com.company;


public class ExceptionPreparing {
    public static void main(String[] args) throws Throwable {
        try {
            Long.parseLong("not-long");
        } catch (NumberFormatException e) {
            /*
                Without "throw" before invalidNumber("reason"):

                Preparing exception...
                Exception in thread "main" java.lang.Exception: Exception thrown because of reason
                    at com.company.ExceptionPreparing.invalidNumber(ExceptionPreparing.java:15)
                    at com.company.ExceptionPreparing.main(ExceptionPreparing.java:9)


                With "throw" before invalidNumber("reason"):

                Preparing exception...
                Exception in thread "main" java.lang.Exception: Exception thrown because of reason
                    at com.company.ExceptionPreparing.invalidNumber(ExceptionPreparing.java:23)
                    at com.company.ExceptionPreparing.main(ExceptionPreparing.java:17)
             */
            throw invalidNumber("reason");
        }
    }

    // "returns Throwable but throws Exception
    // Throwable - type of exception that will be expected in caller method
    // Exception - type of exception that will be actually thrown (must be in throws in method's signature)
    private static Throwable invalidNumber(String reason) throws Exception {
        System.out.println("Preparing exception...");
        throw new Exception("Exception thrown because of " + reason);
    }

}
