package com.compiler.procedure.symboltables;

import com.compiler.model.SymbolTableIndex;
import com.compiler.procedure.tree.Node;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public static Map<SymbolTableIndex,Node> symbolMap = new HashMap<>();
}
