package com.compiler.exception;

public class VariableIsNotDefinedException extends Exception{

    public VariableIsNotDefinedException() {
    }

    public VariableIsNotDefinedException(String message) {
        super("variable "+message+" is not found in symbol table.");
    }
}
