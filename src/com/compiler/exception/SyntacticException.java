package com.compiler.exception;

public class SyntacticException extends Exception{

    public SyntacticException() {}

    public SyntacticException(String message) {
        super("Syntax exception : "+message);
    }

    public SyntacticException(String message, int row) {
        super("Syntax exception at row "+row+" : "+message);
    }
}
