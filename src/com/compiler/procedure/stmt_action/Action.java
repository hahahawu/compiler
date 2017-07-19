package com.compiler.procedure.stmt_action;

import com.compiler.Compiler;
import com.compiler.exception.CastException;
import com.compiler.exception.RedefinitionException;
import com.compiler.exception.VariableIsNotDefinedException;
import com.compiler.model.Stmt;
import com.compiler.model.SymbolTableIndex;
import com.compiler.procedure.symboltables.SymbolTable;
import com.compiler.procedure.tree.Node;

import java.util.ArrayList;
import java.util.Map;

public class Action {
    private static Map<SymbolTableIndex, Node> symbolTable = SymbolTable.symbolMap;
    private static int count = 1;
    private static int seq = 100;
    public static Node takeAction(int stmtNum, ArrayList<Node> children){
        Stmt stmt = DefineStmt.stmts.get(stmtNum);
        Node vn = new Node(stmt.getLeft());
        vn.setChildren(children);
        for (Node child : children){
            child.setParent(vn);
        }
        switch (stmtNum){
            //B -> L
            case 0:
                vn.setScope("scope"+Math.round(Math.random()*65530+1));
                addScopeToAllChildren(vn);
                return vn;
            //S -> E
            case 1:
                return vn;
            //S -> A
            case 2:
                return vn;
            //S -> D
            case 3:
                return vn;
            //D -> D,i
            case 4:
                Node child43 = children.get(0);
                Node child41 = children.get(2);
                vn.setType(child41.getType());
                child43.setType(child41.getType());
                addSymbolTale(child43);
                return vn;
            //D -> int i
            case 5:
                Node child52 = children.get(0);
                Node child51 = children.get(1);
                vn.setType(child51.getId());
                child52.setType(child51.getId());
                addSymbolTale(child52);
                return vn;
            //E -> E+T
            case 6:
                Node child63 = children.get(0);
                Node child61 = children.get(2);
                if (child61.getType() == child63.getType() && child61.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
                    System.out.println(seq+" (+,"+child61.getName()+","+child63.getName()+","+vn.getVal()+")");
                    seq++;
                }
                else {
                    try {
                        throw new CastException("The type of "+child63.getName()+" and "+child61.getName()+"is not matched.", Compiler.row);
                    } catch (CastException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //E -> E-T
            case 7:
                Node child73 = children.get(0);
                Node child71 = children.get(2);
                if (child71.getType() == child73.getType() && child71.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
                    System.out.println(seq+" (-,"+child71.getName()+","+child73.getName()+","+vn.getVal()+")");
                    seq++;
                }
                else {
                    try {
                        throw new CastException("The type of "+child73.getName()+" and "+child71.getName()+"is not matched.", Compiler.row);
                    } catch (CastException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //E -> T
            case 8:
                Node child81 = children.get(0);
                vn.setId(child81.getId());
                vn.setVal(child81.getVal());
                vn.setType(child81.getType());
                vn.setName(child81.getName());
                return vn;
            //T -> F
            case 9:
                Node child91 = children.get(0);
                vn.setId(child91.getId());
                vn.setType(child91.getType());
                vn.setVal(child91.getVal());
                vn.setName(child91.getName());
                return vn;
            //T -> T*F
            case 10:
                Node child103 = children.get(0);
                Node child101 = children.get(2);
                if (child101.getType() == child103.getType() && child101.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
                    System.out.println(seq+" (*,"+child101.getName()+","+child103.getName()+","+vn.getVal()+")");
                    seq++;
                }
                else {
                    try {
                        throw new CastException("The type of "+child103.getName()+" and "+child101.getName()+"is not matched.", Compiler.row);
                    } catch (CastException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //T -> T/F
            case 11:
                return vn;
            //F -> i
            case 12:
                Node child121 = children.get(0);
                if (symbolTable.containsKey(new SymbolTableIndex(child121))){
                    Node child1211 = symbolTable.get(new SymbolTableIndex(child121));
                    child121.setType(child1211.getType());
                    child121.setVal(child1211.getVal());
                    vn.setId(1);
                    vn.setType(child121.getType());
                    vn.setVal(child121.getVal());
                    vn.setName(child121.getName());
                }
                else try {
                    throw new VariableIsNotDefinedException(child121.getName()+" is not found in the symbol table..",Compiler.row);
                } catch (VariableIsNotDefinedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                return vn;
            //F -> (E)
            case 13:
                Node child132 = children.get(1);
                vn.setId(child132.getId());
                vn.setType(child132.getType());
                vn.setVal(child132.getVal());
                vn.setName(child132.getName());
                return vn;
            //F -> d
            case 14:
                Node child141 = children.get(0);
                child141.setType(308);
                vn.setId(10);
                vn.setType(308);
                vn.setVal(child141.getVal());
                vn.setName(child141.getName());
                return vn;
            //A -> i := E
            case 15:
                Node child153 = children.get(0);
                Node child151 = children.get(2);
                if (symbolTable.containsKey(new SymbolTableIndex(child151))){
                    Node child1511 = symbolTable.get(new SymbolTableIndex(child151));
                    if (child1511.getType() == child153.getType()){
                        System.out.println(seq+" (:=,"+child151.getName()+","+child153.getName()+",_)");
                        seq++;
                        child151.setVal(child153.getVal());
                        child1511.setVal(child153.getVal());
                        symbolTable.put(new SymbolTableIndex(child151),child1511);
                    }
                }
                else try {
                    throw new VariableIsNotDefinedException(child151.getName()+" is not found in the symbol table..",Compiler.row);
                } catch (VariableIsNotDefinedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                return vn;
            //L -> S
            case 16:
                return vn;
            //L -> LS
            case 17:
                return vn;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return vn;
    }

    private static void addScopeToAllChildren(Node vn) {
        visit(vn);
    }

    private static void visit(Node vn) {
        if (vn == null || vn.getChildren() == null) return;
        for (Node child : vn.getChildren()){
            child.setScope(vn.getScope());
            visit(child);
            if (child.getId()==1){
                if (symbolTable.containsKey(new SymbolTableIndex(child.getName(),null))){
                    symbolTable.remove(new SymbolTableIndex(child.getName(),null));
                    symbolTable.put(new SymbolTableIndex(child),child);
                }
            }
        }
    }

    private static void addSymbolTale(Node child) {
        if (!symbolTable.containsKey(new SymbolTableIndex(child)))
            symbolTable.put(new SymbolTableIndex(child),child);
        else {
            try {
                throw new RedefinitionException(child.getName()+" have been defined.",Compiler.row);
            } catch (RedefinitionException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
