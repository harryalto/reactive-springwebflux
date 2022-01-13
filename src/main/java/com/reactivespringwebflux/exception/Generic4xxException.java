package com.reactivespringwebflux.exception;


public class Generic4xxException extends UnProcessableEntityException {
    public Generic4xxException(String error) {
        super("Generic 4XX exception" + error);
    }
}