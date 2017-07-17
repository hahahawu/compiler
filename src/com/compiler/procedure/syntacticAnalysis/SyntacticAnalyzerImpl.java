package com.compiler.procedure.syntacticAnalysis;

import com.compiler.exception.IllegalInputException;
import com.compiler.model.Lexical2Syntax;
import com.compiler.model.SLRItem;
import com.compiler.model.Stmt;

import java.util.Map;
import java.util.Stack;


public class SyntacticAnalyzerImpl implements SyntacticAnalyzer{

    private static Stack<Integer> state = new Stack<>();
    private static Stack<String> op = new Stack<>();
    private static Map<SLRItem,String> actionMap = SLRTable.actionMap;
    private static Map<SLRItem, Integer> gotoMap = SLRTable.gotoMap;
    private static Map<Integer,Stmt> stmtMap = SLRTable.stmts;

    static {
        state.push(0);
        op.push("#");
    }

    @Override
    public void syntacticAnalyzer(Lexical2Syntax lexical2Syntax) {
        System.out.println(lexical2Syntax.toString());
        while (true){
            String opName = lexical2Syntax.getIdNumber()==1?"i":lexical2Syntax.getName();
            SLRItem currState = new SLRItem(state.peek(),opName);
            if (actionMap.containsKey(currState)){
                String action = actionMap.get(currState);
                if (action.startsWith("s")){
                    System.out.println(action);
                    int nextState = Integer.parseInt(action.substring(1));
                    state.push(nextState);
                    op.push(opName);
                    break;
                }
                if (action.startsWith("r")){
                    System.out.println(action);
                    int stmtNum = Integer.parseInt(action.substring(1));
                    int num = getStmtRightLen(stmtNum);
                    for (int i=0;i<num;i++) {
                        state.pop();
                        op.pop();
                    }
                    int nextState = gotoMap.get(new SLRItem(state.peek(),getStmtLeft(stmtNum)));
                    state.push(nextState);
                    op.push(getStmtLeft(stmtNum));
                    continue;
                }
                if (action.equalsIgnoreCase("acc")) {
                    System.out.println(action);
                    System.out.println("规约成功!");
                    break;
                }
            }
            else {
                try {
                    throw new IllegalInputException();
                } catch (IllegalInputException e) {
                    e.printStackTrace();
                    System.out.println(currState.toString()+" 规约错误!");
                    System.exit(-1);
                }
            }
        }
    }

    private String getStmtLeft(int i) {
        Stmt stmt = stmtMap.get(i);
        return stmt.getLeft();
    }

    private int getStmtRightLen(int i) {
        Stmt stmt = stmtMap.get(i);
        return stmt.getRight().contains("`") ? stmt.getRight().split("`").length : stmt.getRight().length();
    }
}
