package com.f1soft.bachaat.exception;

public class DataNotFoundException extends RuntimeException
{
    private String message;

    public DataNotFoundException(String message){
        super(message);
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
