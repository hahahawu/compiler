package com.compiler.exception;

public class CastException extends SemanticException{
    public CastException() {}

    public CastException(String message) {
        super("\n\t\t CaseException : "+message);
    }

    public CastException(String message, int row) {
        super("\n\t\t CastException : "+message, row);
    }
}
