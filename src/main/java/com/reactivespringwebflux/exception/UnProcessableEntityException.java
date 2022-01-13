package com.reactivespringwebflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public abstract class UnProcessableEntityException extends RuntimeException {
    UnProcessableEntityException(String message) {
        super(message);
    }
}