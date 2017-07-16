package com.compiler;

import java.util.Map;

public class Test {
    public static void main(String[] args){
//        SLRTable.constructor(0);
//        SLRTable.make_actionTable();
//        lookupMap(SLRTable.actionMap);

    }

    private static void lookupMap(Map map){
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(entry.getKey().toString()+entry.getValue());
        }
    }
}
