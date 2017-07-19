package com.compiler.exception;

public class RedefinitionException extends Exception {
    public RedefinitionException() {super("Redefinition Exception");}

    public RedefinitionException(String name){super("Redefinition Exception : "+name);}

    public RedefinitionException(String message, int row){
        super("Line "+row+" error : "+message);
    }
}
