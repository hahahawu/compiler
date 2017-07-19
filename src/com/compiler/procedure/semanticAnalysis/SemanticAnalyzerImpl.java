package com.compiler.procedure.semanticAnalysis;

import com.compiler.model.Stmt;
import com.compiler.procedure.stmt_action.Action;
import com.compiler.procedure.stmt_action.DefineStmt;
import com.compiler.procedure.tree.Node;

import java.util.ArrayList;

public class SemanticAnalyzerImpl implements SemanticAnalyer{

    @Override
    public Node semanticAnalyzer(int stmtNum, ArrayList<Node> children) {
        Stmt stmt = DefineStmt.stmts.get(stmtNum);
        if (stmt.getRightLength() == children.size()){
            return Action.takeAction(stmtNum,children);
        }
        else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
