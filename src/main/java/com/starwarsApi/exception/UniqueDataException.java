package com.starwarsApi.exception;

public class UniqueDataException extends RuntimeException{
    public UniqueDataException(String message) {
        super(message);
    }
}
