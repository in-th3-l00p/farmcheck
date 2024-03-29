package com.cybertech.farmcheck.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String login) {
        super(String.format("User %s not found.", login));
    }

    public UserNotFoundException(Long id) {
        super(String.format("User %d not found.", id));
    }
}
