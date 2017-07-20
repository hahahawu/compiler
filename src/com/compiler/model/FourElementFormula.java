package com.compiler.model;

public class FourElementFormula {
    private int seq;
    private String op;
    private String arg1;
    private String arg2;
    private String res;

    @Override
    public String toString() {
        return seq+" ("+op+","+arg1+","+arg2+","+res+")";
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public FourElementFormula(int seq, String op, String arg1, String arg2, String res) {

        this.seq = seq;
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.res = res;
    }

    public FourElementFormula(String op, String arg1, String arg2, String res) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.res = res;
    }
}
