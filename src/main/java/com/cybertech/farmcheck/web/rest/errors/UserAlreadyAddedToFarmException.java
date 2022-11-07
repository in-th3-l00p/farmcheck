package com.cybertech.farmcheck.web.rest.errors;

import com.cybertech.farmcheck.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyAddedToFarmException extends Exception {
    public UserAlreadyAddedToFarmException(String userLogin) {
        super(String.format("User %s is already added to the farm.", userLogin));
    }
}
