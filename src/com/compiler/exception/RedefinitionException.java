package com.compiler.exception;

public class RedefinitionException extends Exception {
    public RedefinitionException(String message) {
        super(message);
    }

    public RedefinitionException() {
        super("Redefinition error!");
    }
}
