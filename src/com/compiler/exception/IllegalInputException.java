package com.compiler.exception;

public class IllegalInputException extends Exception{
    public IllegalInputException(){}

    public IllegalInputException(String message, int row) {
        super("Line "+row+" error : "+message);
    }
}
