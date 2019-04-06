package com.company;

import java.util.HashMap;
import java.util.Map;


/*
    Map 1:
    {buildTool=maven, lang=java, IOC=jee}

    Map 2:
    {buildTool=gradle, lang=groovy, IOC=spring}

    Map 1:
    {buildTool=gradle, lang=groovy, IOC=spring}

    Map 2:
    {buildTool=maven, lang=java, IOC=jee}
 */
public class MapSwap {
    public static void main(String... args){
        Map<String, String> map = new HashMap<>();
        map.put("buildTool", "maven");
        map.put("lang", "java");
        map.put("IOC", "jee");

        System.out.println("Map 1:");
        System.out.println(map);
        System.out.println();

        Map<String, String> map2 = new HashMap<>();
        map2.put("buildTool", "gradle");
        map2.put("lang", "groovy");
        map2.put("IOC", "spring");

        System.out.println("Map 2:");
        System.out.println(map2);
        System.out.println();

        map.replaceAll(map2::put);

        System.out.println("Map 1:");
        System.out.println(map);
        System.out.println();
        System.out.println("Map 2:");
        System.out.println(map2);
    }
}
