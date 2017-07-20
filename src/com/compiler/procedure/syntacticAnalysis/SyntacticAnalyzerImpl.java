package com.compiler.procedure.syntacticAnalysis;

import com.compiler.Compiler;
import com.compiler.exception.SyntacticException;
import com.compiler.model.*;
import com.compiler.procedure.semanticAnalysis.SemanticAnalyer;
import com.compiler.procedure.semanticAnalysis.SemanticAnalyzerImpl;
import com.compiler.procedure.stmt_action.Action;
import com.compiler.procedure.stmt_action.DefineStmt;
import com.compiler.procedure.symboltables.SymbolTable;
import com.compiler.procedure.tree.Node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;


public class SyntacticAnalyzerImpl implements SyntacticAnalyzer{

    private static Stack<Integer> state = new Stack<>();
    private static Stack<String> op = new Stack<>();
    private static Map<SLRItem,String> actionMap = SLRTable.actionMap;
    private static Map<SLRItem, Integer> gotoMap = SLRTable.gotoMap;
    private static Map<Integer,Stmt> stmtMap = DefineStmt.stmts;
    private static Stack<Node> nodeStack = new Stack<>();
    private static SemanticAnalyer semanticAnalyer = new SemanticAnalyzerImpl();
    private static Map<Integer,FourElementFormula> fourElementFormulaMap = Action.fourElementFormulaMap;

    static {
        state.push(0);
        op.push("#");
    }

    @Override
    public void syntacticAnalyzer(Lexical2Syntax lexical2Syntax) {
//        System.out.println(lexical2Syntax.toString());
        while (true){
            String opName = lexical2Syntax.getIdNumber()==1?"i": (lexical2Syntax.getIdNumber() == 10 ? "d":lexical2Syntax.getName());
            SLRItem currState = new SLRItem(state.peek(),opName);
            if (actionMap.containsKey(currState)){
                String action = actionMap.get(currState);
                if (action.startsWith("s")){
//                    System.out.println(action);
                    int nextState = Integer.parseInt(action.substring(1));
                    state.push(nextState);
                    op.push(opName);
                    makeNode(lexical2Syntax);
                    break;
                }
                if (action.startsWith("r")){
//                    System.out.println(action);
                    ArrayList<Node> children = new ArrayList<>();
                    int stmtNum = Integer.parseInt(action.substring(1));
                    int num = getStmtRightLen(stmtNum);
                    for (int i=0;i<num;i++) {
                        state.pop();
                        op.pop();
                        children.add(nodeStack.pop());
                    }
                    int nextState = gotoMap.get(new SLRItem(state.peek(),getStmtLeft(stmtNum)));
                    state.push(nextState);
                    op.push(getStmtLeft(stmtNum));
                    nodeStack.push(semanticAnalyer.semanticAnalyzer(stmtNum,children));
                    continue;
                }
                if (action.equalsIgnoreCase("acc")) {
                    System.out.println(action);
                    System.out.println("规约成功!");
                    ArrayList<Node> children = new ArrayList<>();
                    children.add(nodeStack.pop());
                    nodeStack.push(semanticAnalyer.semanticAnalyzer(0,children));
                    System.out.println("--------------four element formula -----------");
                    lookupMap(fourElementFormulaMap);
                    System.out.println("-----------------symbol table-----------------");
                    for (Object o : SymbolTable.symbolMap.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        SymbolTableIndex symbolTableIndex = (SymbolTableIndex) entry.getKey();
                        Node node = (Node) entry.getValue();
                        System.out.println(symbolTableIndex.toString()+node.toString());
                    }
                    break;
                }
            }
            else {
                try {
                    throw new SyntacticException(opName+" is not excepted.", Compiler.row);
                } catch (SyntacticException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            }
        }
    }

    private void makeNode(Lexical2Syntax lexical2Syntax) {
        int nodeTypeID = lexical2Syntax.getIdNumber();
        String nodeName = lexical2Syntax.getName();
        //if the symbol is digit, val should be assigned.
        if (nodeTypeID == 10) nodeStack.push(new Node(nodeName,nodeTypeID,nodeName,308));
        else nodeStack.push(new Node(nodeName,nodeTypeID));
    }

    private String getStmtLeft(int i) {
        Stmt stmt = stmtMap.get(i);
        return stmt.getLeft();
    }

    private int getStmtRightLen(int i) {
        Stmt stmt = stmtMap.get(i);
        return stmt.getRight().contains("`") ? stmt.getRight().split("`").length : stmt.getRight().length();
    }

    private static void lookupMap(Map<Integer, FourElementFormula> fourElementFormulaMap) {
        for (Object o : fourElementFormulaMap.entrySet()){
            Map.Entry entry = (Map.Entry) o;
            FourElementFormula formula = (FourElementFormula) entry.getValue();
            System.out.println(formula.toString());
        }
    }
}
