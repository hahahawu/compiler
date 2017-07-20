package com.compiler;

import com.compiler.model.FourElementFormula;
import com.compiler.model.SymbolTableIndex;
import com.compiler.procedure.stmt_action.Action;
import com.compiler.procedure.symboltables.SymbolTable;
import com.compiler.procedure.tree.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Display {
    public static void display(){
        int seq = Action.getSeq();
        Map<Integer,FourElementFormula> fourElementFormulaMap = Action.fourElementFormulaMap;
        try {
            FileWriter fw = new FileWriter("output.txt",false);
            fw.write("");
            fw = new FileWriter("output.txt",true);
            fw.append("--------------------四元式----------------------\n");
            for (int i=100;i<seq;i++){
                fw.append(fourElementFormulaMap.get(i).toString()).append("\n");
            }
            fw.append("-------------------符号表-----------------------\n");
            for (Object o : SymbolTable.symbolMap.entrySet()){
                Map.Entry entry = (Map.Entry) o;
                SymbolTableIndex symbolTableIndex = (SymbolTableIndex) entry.getKey();
                Node node = (Node) entry.getValue();
                fw.append(symbolTableIndex.toString()).append(node.toString()).append("\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
