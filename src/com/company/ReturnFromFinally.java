package com.company;

public class ReturnFromFinally {

    // Simple description
    // execute try block from 2 to 5, if Exception occurred - jump to 10, otherwise - execute 6 - 9 and return 3
    // execute try block from 2 to 5, if any Throwable (not Exception) occurred - jump to 19, otherwise - execute 6 - 9 and return 3
    // execute catch block from 10 to 14, if Exception occurred - jump to 19, otherwise - execute 15-18 and return 3

    //  #     start   end     handler   catch type
    //  0	    2	    6	    10      cp_info #2 java/lang/Exception
    //  1	    2	    6	    19	    cp_info #0 any
    //  2	    10	    15	    19	    cp_info #0 any

    /*   lol2():
         0 iconst_0 // put constant 0 on stack
         1 istore_0 // int i = 0 (from stack to local variable #0)

         // -------------------- TRY BLOCK FOR EXCEPTION (WITH FINALLY) --------------------

         2 iconst_1 // put constant 1 on stack
         3 istore_0 // i = 1 (from stack to local variable #0)
         4 iload_0  // load i (value from local variable #0 to stack)
         5 istore_1 // store value from stack to local variable #1 before jumping into finally block

                    // FIRST FINALLY FOR TRY{}CATCH(EXCEPION E) -- NO EXCEPTION IN TRY OCCURRED
         6 iconst_3 // put constant 3 on stack
         7 istore_0 // i = 3
         8 iload_0  // load i
         9 ireturn  // return from method with value i = 3 -- return here if no Exception occurred in try block

        // -------------------- CATCH BLOCK FOR EXCEPTION (WITH FINALLY) --------------------

        10 astore_1 // load reference to exception from stack to local variable #1 (and forget about it)
        11 iconst_2 // put constant 2 on stack
        12 istore_0 // i = 2
        13 iload_0  // load i (from local variable #0 to stack)
        14 istore_2 // store value from stack in local variable #2 before jumping into finally block

                    // SECOND FINALLY FOR CATCH BLOCK -- NO THROWABLE IN CATCH OCCURRED
        15 iconst_3 // put constant 3 on stack
        16 istore_0 // i = 3
        17 iload_0  // load i
        18 ireturn  // return from method with value i = 3 -- return here if no Throwable occurred in catch

        // -------------------- FINALLY BLOCK FOR ANY THROWABLE IN TRY OR CATCH --------------------

        19 astore_3 // load exception from stack and forget about it
        20 iconst_3 // put constant 3 on stack
        21 istore_0 // i = 3
        22 iload_0  // load i
        23 ireturn  // return from method with value i = 3 -- return here if Throwable occurred in catch
     */

    private static int lol2() {
        int i = 0;

        try {
            i = 1;
            return i;
        } catch (Exception e) {
            i = 2;
            return i;
        } finally {
            i = 3;
            return i;
        }
    }

    // Simple description
    // execute try block from 2 to 11, if any Exception occurred - execute catch block 12-20, if other Throwable occurred - execute finally 25-29
    // execute catch block from 12 to 20, if no Throwable occurred - execute finally in 21-24, otherwise - execute finally in 25-29

    //  #     start    end     handler   catch type
    //  0	    2	    12	    12	    cp_info #2 java/lang/Exception
    //  1	    2	    17	    21	    cp_info #0 any

    /*   lol1():
         0 iconst_0 // put constant 0 on stack
         1 istore_0 // int i = 0

         // -------------------- TRY BLOCK FOR EXCEPTION (WITHOUT FINALLY) --------------------

         2 iconst_1 // put constant 1 on stack
         3 istore_0 // i = 1 (from stack to local variable #0)
         4 new #2 <java/lang/Exception>
         7 dup
         8 invokespecial #3 <java/lang/Exception.<init>>  // new Exception()
        11 athrow // throw previously created Exception

        // -------------------- CATCH BLOCK FOR EXCEPTION (WITH FINALLY) --------------------

        12 astore_1 // load reference to exception from stack into local variable #1
        13 iconst_2 // put constant 2 on stack
        14 istore_0 // i = 2 (from stack to local variable #0)
        15 aload_1  // put reference to exception on stack
        16 invokevirtual #4 <java/lang/Exception.printStackTrace> // print stacktrace
        19 iload_0  // load value from local variable #0 to stack
        20 istore_2 // store value from stack in local variable #2 before jumping into finally block (it may be our return value)

                    // FIRST FINALLY FOR TRY{}CATCH(EXCEPION E) -- NO EXCEPTION IN CATCH OCCURRED
        21 iconst_3 // put constant 3 on stack
        22 istore_0 // i = 3 (from stack to local variable #0)
        23 iload_0  // load value from local variable #0 to stack
        24 ireturn  // return from method with value i = 3 -- if no exception occurred in try or any exception in catch

        // -------------------- FINALLY BLOCK FOR ANY THROWABLE IN TRY OR CATCH --------------------
        25 astore_3 // load exception from stack and forget about it
        26 iconst_3 // put constant 3 on stack
        27 istore_0 // i = 3
        28 iload_0  // load value from local variable
        29 ireturn  // return from method with value i = 3-- if throwable (not Exception) occurred in try or any exception in catch
     */
    private static int lol1() {
        int i = 0;
        try {
            i = 1;
            throw new Exception();
        } catch (Exception e) {
            i = 2;
            e.printStackTrace();
            return i;
        } finally {
            i = 3;
            return i;
        }
    }

    //  #     start   end     handler   catch type
    //  0	    2	    6	    10      cp_info #2 java/lang/Exception
    //  1	    2	    6	    19	    cp_info #0 any
    //  2	    10	    15	    19	    cp_info #0 any

    /*   lol3():
         0 iconst_0
         1 istore_0

         // -------------------- TRY BLOCK FOR EXCEPTION (WITH FINALLY) --------------------
         2 iconst_1 // put constant 1 on stack
         3 istore_0 // i = 1 (from stack to local variable #0)
         4 iload_0  // load value from stack to local variable #0
         5 istore_1 // put value in local variable #1 before jumping into finally block

                    // FIRST FINALLY FOR TRY{}CATCH(EXCEPION E) -- NO EXCEPTION IN TRY OCCURRED
         6 iconst_3 // put constant 3 on stack
         7 istore_0 // i = 3 (from stack to local variable #0)
         8 iload_1  // load value from local variable #1 to stack
         9 ireturn  // return it, NOT i(3) - but value 1, previously saved in 5

        // -------------------- CATCH BLOCK FOR EXCEPTION (WITH FINALLY) --------------------
        10 astore_1 // load exception from stack to local variable #1
        11 iconst_2 // put constant 2 on stack
        12 istore_0 // i = 2 (from stack to local variable #0)
        13 iload_0  // load value from local variable #0 to stack
        14 istore_2 // store value from stack in local variable #2 before jumping into finally block (i = 2)

                    // FINALLY BLOCK IF NO THROWABLE IN CATCH BLOCK
        15 iconst_3 // put constant 3 on stack
        16 istore_0 // i = 3 (from stack to local variable #0)
        17 iload_2  // load value from local variable #2 to stack
        18 ireturn  // return it, NOT i = 1 - but value i = 2, previously saved in 14

        // -------------------- FINALLY BLOCK FOR ANY THROWABLE IN TRY OR CATCH --------------------
        19 astore_3 // load reference to exception to local variable #3
        20 iconst_3 // put constant 3 on stack
        21 istore_0 // i = 3 (from stack to local variable #0)
        22 aload_3  // load reference to exception from local variable #3 to stack
        23 athrow   // and throw it!
     */

    private static int lol3() {
        int i = 0;
        try {
            i = 1;
            return i;
        } catch (Exception e) {
            i = 2;
            return i;
        } finally {
            i = 3;
        }
    }


    public static void main(String... args) {
        // prints 3
        System.out.println(lol1());
        // prints 3
        System.out.println(lol2());
        // prints 1
        System.out.println(lol3());
    }
}
