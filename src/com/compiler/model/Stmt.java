package com.compiler.model;

public class Stmt {
    private String right;
    private String left;

    public Stmt(String left, String right) {
        this.right = right;
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stmt stmt = (Stmt) o;

        return right.equals(stmt.right) && left.equals(stmt.left);
    }

    @Override
    public int hashCode() {
        return right.hashCode()*100 + left.hashCode()*99;
    }

    public int getRightLength(){
        return right.contains("`") ? right.split("`").length : right.length();
    }
}
