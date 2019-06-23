package com.company;

public class TruncateStringsToBytes {
    public static String truncateWhenUTF8(String s, int maxBytes) {
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int skip = 0;
            int more;
            if (c <= 0x007f) {
                more = 1;
            } else if (c <= 0x07FF) {
                more = 2;
            } else if (c <= 0xd7ff) {
                more = 3;
            } else if (c <= 0xDFFF) {
                more = 4;
                skip = 1;
            } else {
                more = 3;
            }
            if (b + more > maxBytes) {
                return s.substring(0, i);
            }
            b += more;
            i += skip;
        }
        return s;
    }

    public static void main(String[] args){
        System.out.println(truncateWhenUTF8("aaaaaaaaaa", 10));
        System.out.println(truncateWhenUTF8("аааааааааа", 10));
    }
}
