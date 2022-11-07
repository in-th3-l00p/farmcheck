package com.cybertech.farmcheck.web.websocket;

import com.cybertech.farmcheck.domain.Chat;
import com.cybertech.farmcheck.domain.Message;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.ChatService;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.dto.MessageDTO;
import com.cybertech.farmcheck.service.exception.ChatNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessagesController {
    ChatService chatService;

    FarmService farmService;

    @Autowired
    public ChatMessagesController(ChatService chatService, FarmService farmService) {
        this.chatService = chatService;
        this.farmService = farmService;
    }

    @MessageMapping("/chat/sendMessage/{chatId}")
    @SendTo("/topic/{chatId}")
    public MessageDTO sendMessage(
        @DestinationVariable("chatId") Long chatId,
        @Payload MessageDTO messageDTO
    ) throws ChatNotFoundException, UnauthenticatedException, UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Chat chat = chatService.getChat(chatId);

        farmService.checkUserAccess(chat.getFarm(), userLogin);
        return new MessageDTO(
            chatService.addMessage(
                new Message(messageDTO, chat)
            )
        );
    }
}
