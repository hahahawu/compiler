package com.compiler;

import com.compiler.model.ProjectItem;
import com.compiler.model.SLRItem;
import com.compiler.model.SetContainer;
import com.compiler.procedure.syntacticAnalysis.SLRTable;

import java.util.HashSet;
import java.util.Map;

public class Test {
    public static void main(String[] args){
//        lookupMap3(SLRTable.first);
//        lookupMap3(SLRTable.follow);
        lookupMap2(SLRTable.closure);
        lookupMap(SLRTable.actionMap);
        lookupMap(SLRTable.goMap);
    }

    private static void lookupMap3(Map<String, HashSet<String>> first) {
        System.out.println("-------------first && follow------------");
        for (Object o : first.entrySet()){
            Map.Entry entry = (Map.Entry) o;
            System.out.println(entry.getKey());
            HashSet<String> hashSet = (HashSet<String>) entry.getValue();
            for (String str : hashSet) System.out.print(str+"\t");
            System.out.println();
        }
    }

    private static void lookupMap2(Map<Integer, SetContainer> closure) {
        System.out.println("----------------closure-----------------");
        for (Object o : closure.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            HashSet<ProjectItem> hashSet = ((SetContainer)entry.getValue()).getHashSet();
            System.out.println(entry.getKey());
            for (ProjectItem projectItem : hashSet) System.out.println(projectItem.toString());
        }
    }

    private static void lookupMap(Map map){
        System.out.println("------------action && goto--------------");
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            SLRItem slrItem = (SLRItem) entry.getKey();
            System.out.println(slrItem.toString()+entry.getValue());
        }
    }
}
