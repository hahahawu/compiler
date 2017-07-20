package com.compiler.exception;

public class VariableIsNotInitializedException extends SemanticException{
    public VariableIsNotInitializedException() {
        super("\n\t\t VariableIsNotInitializedException.");
    }

    public VariableIsNotInitializedException(String message) {
        super("\n\t\t VariableIsNotInitializedException : variable "+message+" is not initialized.");
    }

    public VariableIsNotInitializedException(String message, int row) {
        super("\n\t\t VariableIsNotInitializedException : variable "+message+" is not initialized.", row);
    }
}
