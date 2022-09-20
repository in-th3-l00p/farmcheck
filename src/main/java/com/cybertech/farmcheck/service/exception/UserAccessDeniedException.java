package com.cybertech.farmcheck.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAccessDeniedException extends Exception {
    public UserAccessDeniedException(
        String userLogin,
        Long farmId
    ) {
        super(String.format("User %s isn't authorized to farm %d.",
            userLogin,
            farmId
        ));
    }
}
