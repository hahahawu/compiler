package com.compiler.procedure.syntacticAnalysis;

import com.compiler.exception.IllegalInputException;
import com.compiler.model.Lexical2Syntax;
import com.compiler.model.Stmt;

import java.util.Map;
import java.util.Stack;


public class SynacticAnalyzerImpl implements SyntacticAnalyzer{

    private static Stack<Integer> state = new Stack<>();
    private static Stack<String> op = new Stack<>();
    private static Map<String,String> actionMap = SLRTable.actionMap;
    private static Map<String,Integer> transMap = SLRTable.transMap;
    private static Map<Integer,Stmt> stmtMap = SLRTable.stmt;

    static {
        state.push(0);
        op.push("#");
    }

    @Override
    public void syntacticAnalyzer(Lexical2Syntax lexical2Syntax) {
        System.out.println(lexical2Syntax.toString());
        while (true){
            String currState = state.peek()+""+lexical2Syntax.getName();
            if (actionMap.containsKey(currState)){
                String action = actionMap.get(currState);
                if (action.startsWith("s")){
                    int nextState = Integer.parseInt(action.substring(1));
                    state.push(nextState);
                    op.push(lexical2Syntax.getName());
                    break;
                }
                if (action.startsWith("r")){
                    int stmtNum = Integer.parseInt(action.substring(1));
                    int num = getStmtRightLen(stmtNum);
                    for (int i=0;i<num;i++) {
                        state.pop();
                        op.pop();
                    }
                    int nextState = transMap.get(state.peek()+""+getStmtLeft(stmtNum));
                    state.push(nextState);
                    op.push(getStmtLeft(stmtNum));
                    continue;
                }
                if (action.equalsIgnoreCase("acc")) {
                    System.out.println("规约成功!");
                    break;
                }
            }
            else {
                try {
                    throw new IllegalInputException();
                } catch (IllegalInputException e) {
                    System.out.println("规约错误!");
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
        return stmt.getRight().length();
    }
}
