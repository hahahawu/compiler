package com.compiler.model;

public class Lexical2Syntax {
    private int idNumber;
    private String name;
    public Lexical2Syntax(int id, String name) {
        this.idNumber = id;
        this.name = name;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id : "+this.idNumber+" ; name : "+this.name;
    }
}
