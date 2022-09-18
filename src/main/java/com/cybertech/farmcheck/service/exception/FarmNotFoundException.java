package com.cybertech.farmcheck.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FarmNotFoundException extends Exception {
    public FarmNotFoundException(String farmName) {
        super(String.format("Farm %s not found.", farmName));
    }
}
