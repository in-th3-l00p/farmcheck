package com.cybertech.farmcheck.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends Exception {
    public UnauthenticatedException() {
        super("The client is unauthenticated.");
    }
}
