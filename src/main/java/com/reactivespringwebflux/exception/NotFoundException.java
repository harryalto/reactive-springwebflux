package com.reactivespringwebflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class NotFoundException extends RuntimeException {

    NotFoundException(String message) {
        super(message);
    }

}