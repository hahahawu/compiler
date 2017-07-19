package com.compiler.exception;

public class IllegalInputException extends LexicalException{
    public IllegalInputException(){}

    public IllegalInputException(String message, int row) {
        super("\n\t\t IllegalInputException :"+message,row);
    }

    public IllegalInputException(String msg){
        super("\n\t\t IllegalInputException ; "+msg);
    }
}
