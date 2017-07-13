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
}
