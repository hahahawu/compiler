package com.compiler;

import com.compiler.model.ProjectItem;
import com.compiler.model.SLRItem;
import com.compiler.model.SetContainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class Test {
    public static void main(String[] args){
//        SLRTable.constructor(0);
//        SLRTable.make_actionTable();
//        lookupMap(SLRTable.first);
//        lookupMap(SLRTable.follow);
//        lookupMap2(SLRTable.closure);
//        lookupMap(SLRTable.actionMap);
//        lookupMap(SLRTable.gotoMap);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);arrayList.add(2);arrayList.add(3);arrayList.add(4);arrayList.add(5);
        for (Integer integer : arrayList) System.out.println(integer);
    }

    private static void lookupMap2(Map<Integer, SetContainer> closure) {
        System.out.println("---------------------------------------");
        for (Object o : closure.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            HashSet<ProjectItem> hashSet = ((SetContainer)entry.getValue()).getHashSet();
            System.out.println(entry.getKey());
            for (ProjectItem projectItem : hashSet) System.out.println(projectItem.toString());
        }
    }

    private static void lookupMap(Map map){
        System.out.println("---------------------------------------");
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            SLRItem slrItem = (SLRItem) entry.getKey();
            System.out.println(slrItem.toString()+entry.getValue());
        }
    }
}
