package com.cybertech.farmcheck.service;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String login) {
        super(String.format("User %s not found.", login));
    }
}
