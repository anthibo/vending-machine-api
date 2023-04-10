package com.example.flapkak.task.common.exceptions;


public class InternalServerException extends RequestException{
    private final static String MESSAGE = "Something wrong happened";
    public InternalServerException() {
        super(MESSAGE);
    }
}
