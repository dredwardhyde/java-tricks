package com.company;


public class ExceptionPreparing {
    /*
        reason
        java.lang.Exception: reason
            at com.company.ExceptionPreparing.invalidNumber(ExceptionPreparing.java:21)
            at com.company.ExceptionPreparing.main(ExceptionPreparing.java:7)
        reason1
        java.lang.RuntimeException: reason1
            at com.company.ExceptionPreparing.invalidNumber(ExceptionPreparing.java:23)
            at com.company.ExceptionPreparing.main(ExceptionPreparing.java:13)
     */
    public static void main(String[] args) {
        try {
            throw invalidNumber("reason");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            throw invalidNumber("reason1");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private static Throwable invalidNumber(String reason)  {
        System.out.println(reason);
        if(reason.equals("reason"))
            return new Exception(reason);
        else
            return new RuntimeException(reason);
    }
}
