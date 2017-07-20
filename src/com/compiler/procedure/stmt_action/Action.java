package com.compiler.procedure.stmt_action;

import com.compiler.Compiler;
import com.compiler.exception.*;
import com.compiler.model.FourElementFormula;
import com.compiler.model.Stmt;
import com.compiler.model.SymbolTableIndex;
import com.compiler.procedure.symboltables.SymbolTable;
import com.compiler.procedure.tree.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Action {
    private static Map<SymbolTableIndex, Node> symbolTable = SymbolTable.symbolMap;
    private static int count = 1;
    private static int seq = 100;
    public static Map<Integer,FourElementFormula> fourElementFormulaMap = new HashMap<>();
    public static Node takeAction(int stmtNum, ArrayList<Node> children){
        Stmt stmt = DefineStmt.stmts.get(stmtNum);
        Node vn = new Node(stmt.getLeft());
        vn.setChildren(children);
        for (Node child : children){
            child.setParent(vn);
        }
        FourElementFormula fourElementFormula;
        FourElementFormula fourElementFormula1;
        Node child;
        Node child1;
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
                child = children.get(0);
                child1 = children.get(2);
                vn.setType(child1.getType());
                child.setType(child1.getType());
                addSymbolTale(child);
                return vn;
            //D -> int i
            case 5:
                child = children.get(0);
                child1 = children.get(1);
                vn.setType(child1.getId());
                child.setType(child1.getId());
                addSymbolTale(child);
                return vn;
            //E -> E+T
            case 6:
                child = children.get(0);
                child1 = children.get(2);
                if (child1.getType() == child.getType() && child1.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
                    fourElementFormula = new FourElementFormula(seq++,"+",child1.getName(),child.getName(),vn.getVal());
//                    System.out.println(fourElementFormula.toString());
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                }
                else {
                    try {
                        throw new CastException("The type of "+child.getName()+" and "+child1.getName()+"is not matched.", Compiler.row);
                    } catch (CastException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //E -> E-T
            case 7:
                child = children.get(0);
                child1 = children.get(2);
                if (child1.getType() == child.getType() && child1.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
//                    System.out.println(seq+" (-,"+child71.getName()+","+child73.getName()+","+vn.getVal()+")");
                    fourElementFormula = new FourElementFormula(seq++,"-",child1.getName(),child.getName(),vn.getVal());
//                    System.out.println(fourElementFormula.toString());
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                }
                else {
                    try {
                        throw new CastException("The type of "+child.getName()+" and "+child1.getName()+"is not matched.", Compiler.row);
                    } catch (CastException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //E -> T
            case 8:
                child1 = children.get(0);
                vn.setId(child1.getId());
                vn.setVal(child1.getVal());
                vn.setType(child1.getType());
                vn.setName(child1.getName());
                return vn;
            //T -> F
            case 9:
                child1 = children.get(0);
                vn.setId(child1.getId());
                vn.setType(child1.getType());
                vn.setVal(child1.getVal());
                vn.setName(child1.getName());
                return vn;
            //T -> T*F
            case 10:
                child = children.get(0);
                child1 = children.get(2);
                if (child1.getType() == child.getType() && child1.getType() == 308){
                    vn.setId(1);
                    vn.setType(308);
                    vn.setVal("T"+count);
                    vn.setName(vn.getVal());
                    count++;
                    fourElementFormula = new FourElementFormula(seq++,"*",child1.getName(),child.getName(),vn.getVal());
//                    System.out.println(fourElementFormula.toString());
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                }
                else {
                    try {
                        throw new CastException("The type of "+child.getName()+" and "+child1.getName()+"is not matched.", Compiler.row);
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
                child = children.get(0);
                if (symbolTable.containsKey(new SymbolTableIndex(child))){
                    child1 = symbolTable.get(new SymbolTableIndex(child));
                    child.setType(child1.getType());
                    child.setVal(child1.getVal());
                    vn.setId(1);
                    vn.setType(child.getType());
                    vn.setVal(child.getVal());
                    vn.setName(child.getName());
                }
                else try {
                    throw new VariableIsNotDefinedException(child.getName()+" is not found in the symbol table..",Compiler.row);
                } catch (VariableIsNotDefinedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                return vn;
            //F -> (E)
            case 13:
                child = children.get(1);
                vn.setId(child.getId());
                vn.setType(child.getType());
                vn.setVal(child.getVal());
                vn.setName(child.getName());
                return vn;
            //F -> d
            case 14:
                child = children.get(0);
                child.setType(308);
                vn.setId(10);
                vn.setType(308);
                vn.setVal(child.getVal());
                vn.setName(child.getName());
                return vn;
            //A -> i := E
            case 15:
                child = children.get(0);
                child1 = children.get(2);
                if (symbolTable.containsKey(new SymbolTableIndex(child1))){
                    Node child1511 = symbolTable.get(new SymbolTableIndex(child1));
                    if (child1511.getType() == child.getType()){
                        fourElementFormula = new FourElementFormula(seq++,":=",child1.getName(),child.getName(),"_");
//                        System.out.println(fourElementFormula.toString());
                        fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                        child1.setVal(child.getVal());
                        child1511.setVal(child.getVal());
                        symbolTable.put(new SymbolTableIndex(child1),child1511);
                    }
                }
                else try {
                    throw new VariableIsNotDefinedException(child1.getName()+" is not found in the symbol table..",Compiler.row);
                } catch (VariableIsNotDefinedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                return vn;
            //L -> S;
            case 16:
                child = children.get(0);
                vn.setChain(child.getChain());
                bp(child.getChain(),seq);
                return vn;
            //L -> LS;
            case 17:
                return vn;
            //D -> boolean i
            case 18:
                child = children.get(0);
                child1 = children.get(1);
                vn.setType(child1.getId());
                child.setType(child1.getId());
                addSymbolTale(child);
                return vn;
            //P^ -> P &&
            case 19:
                child = children.get(1);
                bp(child.getTc(),seq);
                vn.setType(309);
                vn.setFc(child.getFc());
                return vn;
            //P. -> P ||
            case 20:
                child = children.get(1);
                bp(child.getFc(),seq);
                vn.setType(309);
                vn.setTc(child.getTc());
                return vn;
            //P -> P^P
            case 21:
                child = children.get(1);
                child1 = children.get(0);
                vn.setTc(child1.getTc());
                vn.setFc(merge(child.getFc(),child1.getFc()));
                vn.setType(309);
                return vn;
            //P -> P.P
            case 22:
                child = children.get(1);
                child1 = children.get(0);
                vn.setType(309);
                vn.setFc(child1.getFc());
                vn.setTc(merge(child.getTc(),child1.getTc()));
                return vn;
            //P -> !P
            case 23:
                child = children.get(0);
                vn.setTc(child.getFc());
                vn.setFc(child.getTc());
                vn.setName(child.getName());
                vn.setType(child.getType());
                return vn;
            //P -> G:
            case 24:
                child = children.get(0);
                vn.setFc(child.getFc());
                vn.setTc(child.getTc());
                vn.setName(child.getName());
                vn.setType(child.getType());
                return vn;
            //G -> E<E
            case 25:
                child = children.get(2);
                child1 = children.get(0);
                if ((symbolTable.containsKey(new SymbolTableIndex(child)) || child.getId() != 1) &&
                        (symbolTable.containsKey(new SymbolTableIndex(child1)) || child1.getId() != 1)){
                    vn.setType(309);
                    vn.setTc(seq);
                    vn.setFc(seq+1);
                    fourElementFormula = new FourElementFormula(seq++,"j<",child.getName(),child1.getName(),"0");
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                    fourElementFormula1 = new FourElementFormula(seq++,"j","_","_","0");
                    fourElementFormulaMap.put(fourElementFormula1.getSeq(),fourElementFormula1);
                }
                else {
                    try {
                        throw new VariableIsNotInitializedException(
                                symbolTable.containsKey(new SymbolTableIndex(child))? child1.getName() : child.getName());
                    } catch (VariableIsNotInitializedException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //G -> E>E
            case 26:
                child1 = children.get(2);
                child = children.get(0);
                if ((symbolTable.containsKey(new SymbolTableIndex(child)) || child.getId() != 1) &&
                        (symbolTable.containsKey(new SymbolTableIndex(child1)) || child1.getId() != 1)){
                    vn.setType(309);
                    vn.setTc(seq);
                    vn.setFc(seq+1);
                    fourElementFormula = new FourElementFormula(seq++,"j>",child1.getName(),child.getName(),"0");
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                    fourElementFormula1 = new FourElementFormula(seq++,"j","_","_","0");
                    fourElementFormulaMap.put(fourElementFormula1.getSeq(),fourElementFormula1);
                }
                else {
                    try {
                        throw new VariableIsNotInitializedException(
                                symbolTable.containsKey(new SymbolTableIndex(child))? child1.getName() : child.getName());
                    } catch (VariableIsNotInitializedException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //G -> E == E
            case 27:
                child1 = children.get(2);
                child = children.get(0);
                if ((symbolTable.containsKey(new SymbolTableIndex(child)) || child.getId() != 1) &&
                        (symbolTable.containsKey(new SymbolTableIndex(child1)) || child1.getId() != 1)){
                    vn.setType(309);
                    vn.setTc(seq);
                    vn.setFc(seq+1);
                    fourElementFormula = new FourElementFormula(seq++,"j==",child1.getName(),child.getName(),"0");
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                    fourElementFormula1 = new FourElementFormula(seq++,"j","_","_","0");
                    fourElementFormulaMap.put(fourElementFormula1.getSeq(),fourElementFormula1);
                }
                else {
                    try {
                        throw new VariableIsNotInitializedException(
                                symbolTable.containsKey(new SymbolTableIndex(child))? child1.getName() : child.getName());
                    } catch (VariableIsNotInitializedException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //G -> E >= E
            case 28:
                child1 = children.get(2);
                child = children.get(0);
                if ((symbolTable.containsKey(new SymbolTableIndex(child)) || child.getId() != 1) &&
                        (symbolTable.containsKey(new SymbolTableIndex(child1)) || child1.getId() != 1)){
                    vn.setType(309);
                    vn.setTc(seq);
                    vn.setFc(seq+1);
                    fourElementFormula = new FourElementFormula(seq++,"j>=",child1.getName(),child.getName(),"0");
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                    fourElementFormula1 = new FourElementFormula(seq++,"j","_","_","0");
                    fourElementFormulaMap.put(fourElementFormula1.getSeq(),fourElementFormula1);
                }
                else {
                    try {
                        throw new VariableIsNotInitializedException(
                                symbolTable.containsKey(new SymbolTableIndex(child))? child1.getName() : child.getName());
                    } catch (VariableIsNotInitializedException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //G -> E <= E
            case 29:
                child1 = children.get(2);
                child = children.get(0);
                if ((symbolTable.containsKey(new SymbolTableIndex(child)) || child.getId() != 1) &&
                        (symbolTable.containsKey(new SymbolTableIndex(child1)) || child1.getId() != 1)){
                    vn.setType(309);
                    vn.setTc(seq);
                    vn.setFc(seq+1);
                    fourElementFormula = new FourElementFormula(seq++,"j>",child1.getName(),child.getName(),"0");
                    fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                    fourElementFormula1 = new FourElementFormula(seq++,"j","_","_","0");
                    fourElementFormulaMap.put(fourElementFormula1.getSeq(),fourElementFormula1);
                }
                else {
                    try {
                        throw new VariableIsNotInitializedException(
                                symbolTable.containsKey(new SymbolTableIndex(child))? child1.getName() : child.getName());
                    } catch (VariableIsNotInitializedException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                }
                return vn;
            //A -> i := P
            case 30:
                child = children.get(0);
                child1 = children.get(2);
                if (symbolTable.containsKey(new SymbolTableIndex(child1))){
                    Node child3011 = symbolTable.get(new SymbolTableIndex(child1));
                    if (child3011.getType() == child.getType()){
                        child.setVal("T"+count);
                        count++;
                        fourElementFormula = new FourElementFormula(seq++,":=",child1.getName(),child.getVal(),"_");
                        fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                        child3011.setVal(child.getVal());
                        symbolTable.put(new SymbolTableIndex(child1),child3011);
                    }
                }
                else try {
                    throw new VariableIsNotDefinedException(child1.getName()+" is not found in the symbol table..",Compiler.row);
                } catch (VariableIsNotDefinedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                return vn;

            //C -> if P then
            case 31 :
                child = children.get(1);
                if (child.getType() == 309){
                    bp(child.getTc(),seq);
                    vn.setChain(child.getFc());
                }
                else {
                    try {
                        throw new SemanticException();
                    } catch (SemanticException e) {
                        System.exit(-1);
                    }
                }
              return vn;
            //S -> C{L}
            case 32 :
                child = children.get(3);
                child1 = children.get(1);
                vn.setChain(merge(child1.getChain(),child.getChain()));
                return vn;
            //Tp -> C {L} else
            case 33:
                int q = seq;
                child = children.get(4);
                child1 = children.get(2);
                fourElementFormula = new FourElementFormula(seq++,"j","_","_","0");
                fourElementFormulaMap.put(fourElementFormula.getSeq(),fourElementFormula);
                bp(child.getChain(),seq);
                vn.setChain(merge(child1.getChain(),q));
                return vn;
            //S -> Tp {L}
            case 34:
                child = children.get(3);
                child1 = children.get(1);
                vn.setChain(merge(child.getChain(),child1.getChain()));
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

    private static void bp(int index, int seq) {
        FourElementFormula fef = fourElementFormulaMap.get(index);
        try{
            int nxt = Integer.parseInt(fef.getRes());
            fef.setRes(seq+"");
            if (nxt != 0) bp(nxt,seq);
            else return;
        }catch (Exception e){
            return;
        }
    }

    private static int merge(int chain1, int chain2) {
        if (chain1 == 0) return chain2;
        if (chain2 == 0) return chain1;
        FourElementFormula fef1 = fourElementFormulaMap.get(chain1);
        FourElementFormula fef2 = fourElementFormulaMap.get(chain2);
        int nxt = Integer.parseInt(fef1.getRes());
        while (nxt != 0) {
            if (nxt == chain2) return chain1;
            nxt = Integer.parseInt(fourElementFormulaMap.get(nxt).getRes());
        }
        nxt = Integer.parseInt(fef2.getRes());
        int nxtbak = nxt;
        while (nxt != 0) {
            if (nxt == chain1) return chain2;
            nxtbak = nxt;
            nxt = Integer.parseInt(fourElementFormulaMap.get(nxt).getRes());
        }
        if (nxtbak != 0){
            FourElementFormula temp = fourElementFormulaMap.get(nxtbak);
            temp.setRes(chain1+"");
        }
        else fef2.setRes(chain1+"");
        return chain2;
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
