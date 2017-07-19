package com.compiler.exception;

public class SemanticException extends Exception{
    public SemanticException() {}

    public SemanticException(String message) {
        super("Semantic exception : "+message);
    }

    public SemanticException(String message, int row) {
        super("Semantic exception at row "+row+" : "+message);
    }

}
