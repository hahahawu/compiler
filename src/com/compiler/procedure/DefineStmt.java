package com.compiler.procedure;

import com.compiler.model.Stmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefineStmt {
    public static List<String> terminator = new ArrayList<>();
    public static List<String> nonTerminals = new ArrayList<>();
    public static List<String> symbols = new ArrayList<>();
    public static Map<String,String[]> stmtList = new HashMap<>();
    public static Map<Integer,Stmt> stmts = new HashMap<>();

    static {
        terminator.add("int");terminator.add(",");terminator.add(";");terminator.add("-");
        terminator.add(":=");terminator.add("+");terminator.add("*");terminator.add("i");
        terminator.add("/");terminator.add("(");terminator.add(")");
        nonTerminals.add("B");nonTerminals.add("S");nonTerminals.add("T");
        nonTerminals.add("E");nonTerminals.add("D");nonTerminals.add("A");
        nonTerminals.add("F");nonTerminals.add("L");
        symbols.addAll(terminator);
        symbols.addAll(nonTerminals);
        stmts.put(0,new Stmt("B","L"));
        stmts.put(1,new Stmt("S","E"));
        stmts.put(2,new Stmt("S","A"));
        stmts.put(3,new Stmt("S","D"));
        stmts.put(4,new Stmt("D","D,i"));
        stmts.put(5,new Stmt("D","int`i"));
        stmts.put(6,new Stmt("E","E+T"));
        stmts.put(7,new Stmt("E","E-T"));
        stmts.put(8,new Stmt("E","T"));
        stmts.put(9,new Stmt("T","F"));
        stmts.put(10,new Stmt("T","T*F"));
        stmts.put(11,new Stmt("T","T/F"));
        stmts.put(12,new Stmt("F","i"));
        stmts.put(13,new Stmt("F","(E)"));
        stmts.put(14,new Stmt("A","i`:=`E"));
        stmts.put(15,new Stmt("L","S;"));
        stmts.put(16,new Stmt("L","LS;"));
        stmtList.put("B",new String[]{"L"});
        stmtList.put("S",new String[]{"E","A","D"});
        stmtList.put("E",new String[]{"E+T","E-T","T"});
        stmtList.put("D",new String[]{"D,i","int`i"});
        stmtList.put("A",new String[]{"i`:=`E"});
        stmtList.put("T",new String[]{"F","T*F","T/F"});
        stmtList.put("F",new String[]{"i","(E)"});
        stmtList.put("L",new String[]{"S;","LS;"});
    }

}
