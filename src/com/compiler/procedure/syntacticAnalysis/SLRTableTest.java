package com.compiler.procedure.syntacticAnalysis;

import com.compiler.model.Stmt;

import java.util.HashMap;
import java.util.Map;

public class SLRTableTest {
    public static String[][] action = {
            {"s5",null,null,"s4",null,null},
            {null,"s6",null,null,null,"acc"},
            {null,"r2","s7",null,"r2","r2"},
            {null,"r4","r4",null,"r4","r4"},
            {"s5",null,null,"s4",null,null},
            {null,"r6","r6",null,"r6","r6"},
            {"s5",null,null,"s4",null,null},
            {"s5",null,null,"s4",null,null},
            {null,"s6",null,null,"s11",null},
            {null,"r1","s7",null,"r1","r1"},
            {null,"r3","r3",null,"r3","r3"},
            {null,"r5","r5",null,"r5","r5"}
    };
    private static String[] terminal = {"i","+","*","(",")","#"};
    private static String[] unterminal = {"E","T","F"};
    public static int[][] trans = {
            {1,2,3},{-1,-1,-1},{-1,-1,-1},{-1,-1,-1},
            {8,2,3},{-1,-1,-1},{-1,9,3},{-1,-1,10},
            {-1,-1,-1},{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}
    };
    public static Map<String,String> actionMap = new HashMap<>();
    public static Map<String,Integer> transMap = new HashMap<>();
    public static Map<Integer,Stmt> stmt = new HashMap<>();
    static {
        for (int i=0;i<action.length;i++)
            for (int j=0;j<action[0].length;j++){
                if (action[i][j] != null) {
                    actionMap.put(i+""+terminal[j],action[i][j]);
                }
            }
        for (int i=0;i<trans.length;i++)
            for (int j=0;j<trans[0].length;j++){
                if (trans[i][j] != -1) {
                    transMap.put(i+""+unterminal[j],trans[i][j]);
                }
            }
        stmt.put(1,new Stmt("E","E+T"));
        stmt.put(2,new Stmt("E","T"));
        stmt.put(3,new Stmt("T","T*F"));
        stmt.put(4,new Stmt("T","F"));
        stmt.put(5,new Stmt("F","(E)"));
        stmt.put(6,new Stmt("F","i"));
    }
}
