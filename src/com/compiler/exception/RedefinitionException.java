package com.compiler.exception;

public class RedefinitionException extends Exception {
    public RedefinitionException() {}

    public RedefinitionException(String message, int row){
        super("Line "+row+" error : "+message);
    }
}
