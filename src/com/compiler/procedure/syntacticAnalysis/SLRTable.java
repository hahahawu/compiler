package com.compiler.procedure.syntacticAnalysis;

import com.compiler.model.SLRItem;
import com.compiler.model.ProjectItem;
import com.compiler.model.SetContainer;
import com.compiler.model.Stmt;
import com.compiler.procedure.stmt_action.DefineStmt;

import java.util.*;

public class SLRTable {
    private static List<String> terminator = DefineStmt.terminator;
    private static List<String> nonTerminals = DefineStmt.nonTerminals;
    private static List<String> symbols = DefineStmt.symbols;
    private static Map<String,String[]> stmtList = DefineStmt.stmtList;
    private static Map<Integer,Stmt> stmts = DefineStmt.stmts;
    public static Map<SLRItem,String> actionMap = new HashMap<>();
    public static Map<SLRItem, Integer> gotoMap = new HashMap<>();
    public static Map<Integer,SetContainer> closure = new HashMap<>();
    private static boolean[] flags;
    private static int count = 0;
    public static Map<SLRItem,Integer> goMap = new HashMap<>();
    public static Map<String,HashSet<String>> first = new HashMap<>();
    public static Map<String,HashSet<String>> follow = new HashMap<>();
    private static boolean[] fst;
    private static boolean[] followFlag;

    static {
        flags = new boolean[nonTerminals.size()];
        fst = new boolean[nonTerminals.size()];
        followFlag = new boolean[nonTerminals.size()];
        ProjectItem tempPro = new ProjectItem("B","L",0);
        closure.put(0,new SetContainer(make_project(tempPro,false)));
        closure.get(0).getHashSet().add(tempPro);
        for (String vn : nonTerminals) make_first(vn,0);
        for (String vn : nonTerminals) make_follow(vn,0);
        constructor(0);
        make_actionTable();
    }

    /**
     * construct slr table
     * @param index closure index
     */
    public static void constructor(int index){
        int preMapSize = closure.size();
        HashSet<ProjectItem> currSet = closure.get(index).getHashSet();
        HashSet<ProjectItem> newSet;
        for (String symbol : symbols){
            newSet = new HashSet<>();
            for (ProjectItem projectItem : currSet){
                String nextSymbol = projectItem.getNextSymbol();
                if (!projectItem.atLast() && projectItem.getNextSymbol().equals(symbol)){
                    int strLen = nextSymbol.length() == 1 ?
                            (projectItem.getLocation() == projectItem.getRight().length()-1 ||
                                    projectItem.getRight().charAt(projectItem.getLocation()+1) != '`' ? 1 : 2)
                            : nextSymbol.length()+1;
                    projectItem.setLocation(projectItem.getLocation()+strLen);
                    newSet.addAll(make_project(projectItem,false));
                    projectItem.setLocation(projectItem.getLocation()-strLen);
                    newSet.add(new ProjectItem(projectItem.getLeft(),projectItem.getRight(),projectItem.getLocation()+strLen));
                }
            }
            if (!newSet.isEmpty()) {
                if (!closure.containsValue(new SetContainer(newSet))){
                    closure.put(++count,new SetContainer(newSet));
                    goMap.put(new SLRItem(index,symbol),count);
                    if (isNonTerminal(symbol))
                        gotoMap.put(new SLRItem(index,symbol),count);
                }
                else {
                    SLRItem slrItem = new SLRItem(index,symbol);
                    int key = getKeyByValue(new SetContainer(newSet));
                    if (key != -1){
                        goMap.put(slrItem,key);
                        if (isNonTerminal(symbol)) gotoMap.put(slrItem,key);
                    }
                    else {
                        closure.put(++count,new SetContainer(newSet));
                        goMap.put(new SLRItem(index,symbol),count);
                        if (isNonTerminal(symbol))
                            gotoMap.put(new SLRItem(index,symbol),count);
                    }
                }
            }
        }
        if (closure.size() != preMapSize || index < count) constructor(index+1);
    }

    private static int getKeyByValue(SetContainer setContainer) {
        for (Object o : closure.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue().equals(setContainer)) return (Integer) entry.getKey();
        }
        return -1;
    }

    private static int getKeyByValue(Stmt stmt) {
        for (Object o : stmts.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue().equals(stmt)) return (Integer) entry.getKey();
        }
        return -1;
    }

    /**
     * create action table
     */
    public static void make_actionTable(){
        terminator.add("#");
        for (Object o : closure.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            int index = (int)entry.getKey();
            for (ProjectItem projectItem : ((SetContainer) entry.getValue()).getHashSet()){
                SLRItem tempItem = new SLRItem(index,projectItem.getNextSymbol());
                if (projectItem.atLast()){
                    int stmtNum = getKeyByValue(new Stmt(projectItem.getLeft(),projectItem.getRight()));
                    for (String ter : terminator){
                        if (projectItem.getLeft().equalsIgnoreCase("B"))
                            actionMap.put(new SLRItem(index,"#"),"acc");
                        else {
                            if (follow.get(projectItem.getLeft()).contains(ter))
                                if (!actionMap.containsKey(new SLRItem(index,ter)))
                                actionMap.put(new SLRItem(index,ter),"r"+stmtNum);
                                else try {
                                    throw new Exception();
                                } catch (Exception e) {
//                                    System.out.println(index+" : while "+ter+" "+actionMap.get(new SLRItem(index,ter))+" vs r"+stmtNum);
                                    actionMap.put(new SLRItem(index,ter),"r"+stmtNum);
                                }
                        }
                    }
                }
                else if (isTerminal(projectItem.getNextSymbol()) && goMap.containsKey(tempItem)){
                    if (!actionMap.containsKey(tempItem)) actionMap.put(tempItem,"s"+goMap.get(tempItem));
                    else try {
                        throw new Exception();
                    } catch (Exception e) {
//                        System.out.println(index+" : while "+projectItem.getNextSymbol()+" "+actionMap.get(tempItem)+" vs s"+goMap.get(tempItem));
                    }
                }
                else if (isNonTerminal(projectItem.getNextSymbol()) && gotoMap.containsKey(tempItem)) continue;
                else {
                    try {
                        throw new  Exception();
                    } catch (Exception e) {
                        System.out.println(projectItem.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * make projects from a given project
     * @param projectItem the given project
     * @param iteration whether init flag array
     * @return projects that have been constructed from given project
     */
    private static HashSet<ProjectItem> make_project(ProjectItem projectItem , boolean iteration){
        if (!iteration) flags = new boolean[nonTerminals.size()];
        HashSet<ProjectItem> returnSet = new HashSet<>();
        int curr = projectItem.getLocation();
        if (curr == projectItem.getRight().length()) return returnSet;
        else if (isNonTerminal(projectItem.getNextSymbol())){
            String tagSymbol = projectItem.getNextSymbol();
            String[] tempStmts = stmtList.get(tagSymbol);
            for (String item : tempStmts){
                String nextFirstSymbol = getNextSymbol(item,0);
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

    private static String getNextSymbol(String item, int location) {
        boolean flag = false;
        if (item.charAt(location) == '`' && location != item.length()-1) location++;
        int i = location;
        while (i<item.length()){
            if (item.charAt(i) == '`') return item.substring(location,i);
            else {
                flag = true;
                i++;
            }
        }
        return flag ? item.charAt(location)+"" : "";
    }

    private static boolean isNonTerminal(String str) {
        return nonTerminals.contains(str);
    }

    private static boolean isTerminal(String str){
        return terminator.contains(str);
    }

    private static HashSet<String> make_first(String vn, int index){
        if (fst[nonTerminals.indexOf(vn)]) return first.get(vn);
        fst[nonTerminals.indexOf(vn)] = true;
        HashSet<String> vnFirst = new HashSet<>();
        first.put(vn,vnFirst);
        for (String hxs : stmtList.get(vn)){
            if (isTerminal(getNextSymbol(hxs,index))) vnFirst.add(getNextSymbol(hxs,index));
            else {
                String nextVn = getNextSymbol(hxs,index);
                try {
                    if (fst[nonTerminals.indexOf(nextVn)] && !nextVn.equals(vn)){
                        for (String nxtFst : first.get(nextVn)){
                            if (nxtFst.equalsIgnoreCase("@")) vnFirst.addAll(make_first(vn,index+1));
                            else vnFirst.add(nxtFst);
                        }
                    }
                    else vnFirst.addAll(make_first(nextVn,0));
                }catch (Exception e){
                    System.out.println(vn+" "+hxs+" "+index+" "+nextVn);
                    System.exit(-1);
                }
            }
        }
        first.put(vn,vnFirst);
        return vnFirst;
    }

    private static HashSet<String> make_follow(String vn, int index){
        if (followFlag[nonTerminals.indexOf(vn)]) return follow.get(vn);
        HashSet<String> vnFollow = new HashSet<>();
        follow.put(vn,vnFollow);
        followFlag[nonTerminals.indexOf(vn)] = true;
        if (vn.equalsIgnoreCase("B")) vnFollow.add("#");
        for (Object o : stmts.entrySet()){
            Map.Entry entry = (Map.Entry) o;
            String currStmtRight = ((Stmt)entry.getValue()).getRight();
            String currStmtLeft = ((Stmt)entry.getValue()).getLeft();
            if (currStmtRight.contains(vn)){
                for (int i=index;i<currStmtRight.length();i++){
                    if (currStmtRight.charAt(i) == '`') continue;
                    if (getNextSymbol(currStmtRight,i).equalsIgnoreCase(vn)){
                        if (i < currStmtRight.length()-1 && isTerminal(getNextSymbol(currStmtRight,i+vn.length())))
                            vnFollow.add(getNextSymbol(currStmtRight,i+ vn.length()));
                        else if (i<currStmtRight.length()-1 && isNonTerminal(getNextSymbol(currStmtRight,i+vn.length()))){
                            String nextVn = getNextSymbol(currStmtRight,i+vn.length());
                            for (String nxtFlw : first.get(nextVn)){
                                if (nxtFlw.equalsIgnoreCase("@")) {
                                    if (i+1 != currStmtRight.length()-1) vnFollow.addAll(make_follow(vn,index+nextVn.length()));
                                    else vnFollow.addAll(addLeftFollow(currStmtLeft));
                                }
                                else vnFollow.add(nxtFlw);
                            }
                        }
                        else if (i == currStmtRight.length()-1){
                            vnFollow.addAll(addLeftFollow(currStmtLeft));
                        }
                    }
                }
            }

        }
        return vnFollow;
    }

    private static HashSet<String> addLeftFollow(String left) {
        return make_follow(left,0);
    }

}
