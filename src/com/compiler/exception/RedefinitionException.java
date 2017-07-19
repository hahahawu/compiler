package com.compiler.exception;

public class RedefinitionException extends SemanticException {
    public RedefinitionException() {}

    public RedefinitionException(String name){
        super("\n\t\t RedefinitionException : "+name);
    }

    public RedefinitionException(String message, int row){
        super("\n\t\t RedefinitionException : "+message,row);
    }
}
