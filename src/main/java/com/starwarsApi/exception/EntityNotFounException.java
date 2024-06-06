package com.starwarsApi.exception;

public class EntityNotFounException extends  RuntimeException{
    public EntityNotFounException(String message) {
        super(message);
    }
}
