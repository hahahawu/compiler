package com.compiler.procedure.syntacticAnalysis;

import com.compiler.model.ProjectItem;
import com.compiler.model.Stmt;

import java.util.*;

public class SLRTable {
    private static List<String> terminator = new ArrayList<>();
    private static List<String> nonTerminals = new ArrayList<>();
    private static Map<String,String[]> stmtList = new HashMap<>();
    public static Map<Integer,Stmt> stmts = new HashMap<>();
    public static Map<String,String> actionMap = new HashMap<>();
    public static Map<String,Integer> gotoMap = new HashMap<>();
    public static Map<Integer,HashSet<ProjectItem>> closure = new HashMap<>();
    private static boolean[] flags;

    static {
        terminator.add("+");terminator.add("*");terminator.add("(");terminator.add(")");terminator.add("i");
        nonTerminals.add("S");nonTerminals.add("E");
        nonTerminals.add("T");nonTerminals.add("F");
        flags = new boolean[nonTerminals.size()];
        stmts.put(0,new Stmt("S","E"));
        stmts.put(1,new Stmt("E","E+T"));
        stmts.put(2,new Stmt("E","T"));
        stmts.put(3,new Stmt("T","T*F"));
        stmts.put(4,new Stmt("T","F"));
        stmts.put(5,new Stmt("F","(E)"));
        stmts.put(6,new Stmt("F","i"));
        stmtList.put("S",new String[]{"E"});
        stmtList.put("E",new String[]{"E+T","T"});
        stmtList.put("T",new String[]{"T*F","F"});
        stmtList.put("F",new String[]{"(E)","i"});
        closure.put(0,make_project(new ProjectItem("S","E",0),false));
    }

    public static void constructor(){

    }

    private static HashSet<ProjectItem> make_project(ProjectItem projectItem , boolean iteration){
        if (!iteration) flags = new boolean[nonTerminals.size()];
        HashSet<ProjectItem> returnSet = new HashSet<>();
        returnSet.add(projectItem);
        int curr = projectItem.getLocation();
        if (curr == projectItem.getRight().length()) return returnSet;
        else if (isNonTerminal(projectItem.getRight().charAt(curr)+"")){
            String tagSymbol = projectItem.getRight().charAt(curr)+"";
            String[] tempStmts = stmtList.get(tagSymbol);
            for (String item : tempStmts){
                String nextFirstSymbol = item.charAt(0)+"";
                if (isNonTerminal(nextFirstSymbol) && !flags[nonTerminals.indexOf(nextFirstSymbol)]) {
                    flags[nonTerminals.indexOf(nextFirstSymbol)] = true;
                    HashSet<ProjectItem> tempProSet = make_project(new ProjectItem(tagSymbol,item,0),true);
                    returnSet.addAll(tempProSet);
                }
                returnSet.add(new ProjectItem(tagSymbol,item,0));
            }
        }
        return returnSet;
    }

    private static boolean isNonTerminal(String str) {
        return nonTerminals.contains(str);
    }

    public static void lookupMap(Map map){
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println("key : "+entry.getKey()+" value : "+entry.getValue());
        }
    }

    public static void lookupSet(Set<ProjectItem> set){
        for (ProjectItem projectItem : set) System.out.println(projectItem.toString());
    }

}
