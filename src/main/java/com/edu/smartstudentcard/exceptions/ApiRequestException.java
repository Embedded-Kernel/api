package com.edu.smartstudentcard.exceptions;

public class ApiRequestException  extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}