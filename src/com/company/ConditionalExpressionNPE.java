package com.company;

public class ConditionalExpressionNPE {
    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html
    public static void main(String... args){
        boolean isQuestion = false;
        // second isQuestion ? 42 : null is evaluated first
        // return type determined by table
        // Table 15.25-E. Conditional expression type (Reference 3rd operand, Part III)
        // 3-rd operand in null,
        // 2-nd operand is int,
        // return type is lub(Integer,null)
        // lub described here https://stackoverflow.com/a/43507503
        // so second isQuestion ? 42 : null casts null to Integer and produces NPE
        // informally, lub(T_1, ..., T_n) is the most specific type that's simultaneously a supertype of T_1,...,T_n
        // in our case it is lub(Integer, null) -> Integer
        System.out.println(isQuestion ? 42 : isQuestion ? 42 : null); // NPE
    }
}
