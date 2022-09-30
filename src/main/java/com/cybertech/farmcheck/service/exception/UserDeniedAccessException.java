package com.cybertech.farmcheck.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserDeniedAccessException extends Exception {
    public UserDeniedAccessException(String userLogin, Long farmId) {
        super(String.format("User %s doesn't have access to farm %d.", userLogin, farmId));
    }
}
