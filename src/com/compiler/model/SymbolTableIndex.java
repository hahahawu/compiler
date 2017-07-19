package com.compiler.model;

import com.compiler.procedure.tree.Node;

public class SymbolTableIndex {
    private String name;
    private String scope;

    public SymbolTableIndex(String name, String scope) {
        this.name = name;
        this.scope = scope;
    }

    public SymbolTableIndex(Node node){
        this.name = node.getName();
        this.scope = node.getScope();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SymbolTableIndex that = (SymbolTableIndex) o;

        if (scope == null)
        return name.equals(that.name);
        else return name.equals(that.name) && scope.equals(that.scope);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
       return "("+name+","+scope+") -> ";
    }
}
