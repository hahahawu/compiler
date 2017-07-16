package com.compiler.model;

public class SLRItem {
    private int closure;
    private String input;

    public SLRItem(Integer closure, String input) {
        this.closure = closure;
        this.input = input;
    }


    public Integer getClosure() {
        return closure;
    }

    public void setClosure(Integer closure) {
        this.closure = closure;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SLRItem goIndex = (SLRItem) o;

        return closure == goIndex.getClosure() && input.equalsIgnoreCase(goIndex.getInput());
    }

    @Override
    public int hashCode() {
        return closure + input.hashCode();
    }

    @Override
    public String toString() {
       return "("+closure+","+input+") -> ";
    }
}
