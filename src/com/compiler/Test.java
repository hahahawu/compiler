package com.compiler;

import com.compiler.model.ProjectItem;
import com.compiler.model.SetContainer;
import com.compiler.procedure.syntacticAnalysis.SLRTable;

import java.util.Map;

public class Test {
    public static void main(String[] args){
        SLRTable.constructor(0);
//        SLRTable.make_actionTable();
        lookupMap(SLRTable.closure);

    }

    private static void lookupMap(Map map){
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(entry.getKey());
            for (ProjectItem projectItem : ((SetContainer)entry.getValue()).getHashSet()) System.out.println(projectItem.toString());
        }
    }
}
