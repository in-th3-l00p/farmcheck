package com.cybertech.farmcheck.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends Exception {
    public ChatNotFoundException(Long chatId) {
        super(String.format("Chat %d not found.", chatId));
    }
}
