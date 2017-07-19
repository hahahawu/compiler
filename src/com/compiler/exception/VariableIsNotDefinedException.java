package com.compiler.exception;

public class VariableIsNotDefinedException extends SemanticException{

    public VariableIsNotDefinedException() {}

    public VariableIsNotDefinedException(String message) {
        super("\n\t\t VariableIsNotDefinedException : "+message);
    }

    public VariableIsNotDefinedException(String message, int row) {
        super("\n\t\t VariableIsNotDefinedException : "+message, row);
    }
}
