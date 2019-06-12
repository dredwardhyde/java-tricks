package com.company;

/*
    From JLS https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.13.3
    The timing of method reference expression evaluation is more complex than that of lambda expressions (§15.27.4).
    When a method reference expression has an expression (rather than a type) preceding the :: separator,
    that subexpression is evaluated immediately. The result of evaluation is stored until the method of the
    corresponding functional interface type is invoked; at that point, the result is used as the target reference
    for the invocation. This means the expression preceding the :: separator is evaluated only when the program
    encounters the method reference expression, and is not re-evaluated on subsequent invocations on the functional
    interface type.

    It is interesting to contrast the treatment of null here with its treatment during method invocation.
    When a method invocation expression is evaluated, it is possible for the Primary that qualifies the invocation
    to evaluate to null but for no NullPointerException to be raised. This occurs when the invoked method is static
    (despite the syntax of the invocation suggesting an instance method). Since the applicable method for a method
    reference expression qualified by a Primary is prohibited from being static (§15.13.1), the evaluation of
    the method reference expression is simpler - a null Primary always raises a NullPointerException.
*/
public class MethodReferenceVsLambda {
    private static InnerClass someObject = new InnerClass(1);
    private static Runnable ref = someObject::printId;
    private static Runnable lambda = () -> someObject.printId();

    /*
        Output:
        1
        1
        1
        2
        1
        NPE here
     */
    public static void main(String[] args){
        ref.run();
        lambda.run();
        someObject = new InnerClass(2);
        ref.run();
        lambda.run();
        someObject = null;
        ref.run();
        try {
            lambda.run();
        }catch (NullPointerException e){
            System.out.println("NPE here");
        }
    }

    static class InnerClass {
        private final int id;
        InnerClass(int id){
            this.id = id;
        }
        void printId(){
            System.out.println(id);
        }
    }
}
