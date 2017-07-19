package com.compiler.exception;

public class LexicalException extends Exception {
    public LexicalException() {}

    public LexicalException(String message) {
        super("Lexical exception : "+message);
    }

    public LexicalException(String message,int row) {
        super("Lexical exception at row "+row+" : "+message);
    }
}
