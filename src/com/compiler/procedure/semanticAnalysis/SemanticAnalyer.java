package com.compiler.procedure.semanticAnalysis;

import com.compiler.procedure.tree.Node;

import java.util.ArrayList;

public interface SemanticAnalyer {
    Node semanticAnalyzer(int stmtNum, ArrayList<Node> children);
}
