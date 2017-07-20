package com.compiler.procedure.stmt_action;

import com.compiler.model.Stmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefineStmt {
    public static ArrayList<String> terminator = new ArrayList<>();
    public static List<String> nonTerminals = new ArrayList<>();
    public static List<String> symbols = new ArrayList<>();
    public static Map<String,String[]> stmtList = new HashMap<>();
    public static Map<Integer,Stmt> stmts = new HashMap<>();

    static {
        terminator.add("int");terminator.add(",");terminator.add(";");terminator.add("-");
        terminator.add(":=");terminator.add("+");terminator.add("*");terminator.add("i");
        terminator.add("/");terminator.add("(");terminator.add(")");terminator.add("d");
        terminator.add("boolean");terminator.add("&&");terminator.add("||");
        terminator.add("!");terminator.add("<");terminator.add(">");
        terminator.add("<=");terminator.add(">=");terminator.add("==");
        terminator.add("{");terminator.add("}");terminator.add("if");terminator.add("else");
        terminator.add("then");terminator.add("while");terminator.add("do");
        terminator.add("[");terminator.add("]");

        nonTerminals.add("B");nonTerminals.add("S");nonTerminals.add("T");
        nonTerminals.add("D");nonTerminals.add("A");nonTerminals.add("E");
        nonTerminals.add("F");nonTerminals.add("L");nonTerminals.add("P");
        nonTerminals.add("P.");nonTerminals.add("P^");nonTerminals.add("G");
        nonTerminals.add("C");nonTerminals.add("Tp");nonTerminals.add("W");
        nonTerminals.add("Wd");nonTerminals.add("Dl");nonTerminals.add("V");
        nonTerminals.add("El");

        symbols.addAll(terminator);
        symbols.addAll(nonTerminals);
        stmts.put(0,new Stmt("B","L"));
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
        stmts.put(14,new Stmt("F","d"));
        stmts.put(15,new Stmt("A","i`:=`E"));
        stmts.put(16,new Stmt("L","S;"));
        stmts.put(17,new Stmt("L","LS;"));

        stmts.put(18,new Stmt("D","boolean`i"));
        stmts.put(19,new Stmt("P^","P`&&`"));
        stmts.put(20,new Stmt("P.","P`||`"));
        stmts.put(21,new Stmt("P","P^`P"));
        stmts.put(22,new Stmt("P","P.`P"));
        stmts.put(23,new Stmt("P","!P"));
        stmts.put(24,new Stmt("P","G"));

        stmts.put(25,new Stmt("G","E<E"));
        stmts.put(26,new Stmt("G","E>E"));
        stmts.put(27,new Stmt("G","E`==`E"));
        stmts.put(28,new Stmt("G","E`>=`E"));
        stmts.put(29,new Stmt("G","E`<=`E"));
        stmts.put(30,new Stmt("A","i`:=`P"));

        stmts.put(31,new Stmt("C","if`P`then`"));
        stmts.put(32,new Stmt("S","C{L}"));
        stmts.put(33,new Stmt("Tp","C`{`L`}`else`"));
        stmts.put(34,new Stmt("S","Tp`{`L`}`"));

        stmts.put(35,new Stmt("W","while`"));
        stmts.put(36,new Stmt("Wd","W`P`do`"));
        stmts.put(37,new Stmt("S","Wd`{`L`}`"));

        stmts.put(38,new Stmt("Dl","do`{`L`}`"));
        stmts.put(39,new Stmt("S","Dl`while`(`P`)`"));

        stmts.put(40,new Stmt("D","int`V"));
        stmts.put(41,new Stmt("D","D,V"));
        stmts.put(42,new Stmt("A","V`:=`E"));
        stmts.put(43,new Stmt("V","El`]"));
        stmts.put(44,new Stmt("El","El`,`E"));
        stmts.put(45,new Stmt("El","i`[`E"));
        stmts.put(46,new Stmt("E","V"));

        stmtList.put("B",new String[]{"L"});
        stmtList.put("S",new String[]{"A","D","C{L}","Tp`{`L`}`","Wd`{`L`}`","Dl`while`(`P`)`"});
        stmtList.put("E",new String[]{"E+T","E-T","T","V"});
        stmtList.put("D",new String[]{"D,i","int`i","boolean`i","int`V","D,V"});
        stmtList.put("A",new String[]{"i`:=`E","i`:=`P","V`:=`E"});
        stmtList.put("V",new String[]{"El`]"});
        stmtList.put("El",new String[]{"El`,`E","i`[`E"});
        stmtList.put("T",new String[]{"F","T*F","T/F"});
        stmtList.put("F",new String[]{"i","(E)","d"});
        stmtList.put("L",new String[]{"S;","LS;"});
        stmtList.put("P^",new String[]{"P`&&`"});
        stmtList.put("P.",new String[]{"P`||`"});
        stmtList.put("P",new String[]{"!P","G","P.`P","P^`P"});
        stmtList.put("G",new String[]{"E<E","E>E","E`==`E","E`>=`E","E`<=`E"});
        stmtList.put("C",new String[]{"if`P`then`"});
        stmtList.put("Tp",new String[]{"C`{`L`}`else`"});
        stmtList.put("W",new String[]{"while`"});
        stmtList.put("Wd",new String[]{"W`P`do`"});
        stmtList.put("Dl",new String[]{"do`{`L`}`"});
    }

}
