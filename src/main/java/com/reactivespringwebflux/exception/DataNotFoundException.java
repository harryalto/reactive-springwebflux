package com.reactivespringwebflux.exception;


public class DataNotFoundException extends NotFoundException {
    public DataNotFoundException(Integer id) {
        super(String.format("Item [%d] is not found", id));
    }
}