package com.company;

class SuperB {{
    System.out.println(this);
}}

public class OrderPuzzle extends SuperB {
    {
        System.out.println("1 ");
        System.out.println(this);
    }

    final byte x = 1;
          byte b = 1;
    final Byte y = 1;
          Byte z = 1;
    {
        System.out.println("2");
        System.out.println(this);
    }

    public OrderPuzzle(){
        System.out.println(3);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "OrderPuzzle{" +
                "x=" + x +
                ", b=" + b +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
    /*
        OrderPuzzle{x=1, b=0, y=null, z=null}
        1
        OrderPuzzle{x=1, b=0, y=null, z=null}
        2
        OrderPuzzle{x=1, b=1, y=1, z=1}
        3
        OrderPuzzle{x=1, b=1, y=1, z=1}
     */
    public static void main(String... args){
        new OrderPuzzle();
    }
}
