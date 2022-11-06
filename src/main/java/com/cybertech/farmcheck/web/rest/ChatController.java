package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.domain.Chat;
import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.security.SecurityUtils;
import com.cybertech.farmcheck.service.ChatService;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.UserService;
import com.cybertech.farmcheck.service.dto.ChatDTO;
import com.cybertech.farmcheck.service.dto.MessageDTO;
import com.cybertech.farmcheck.service.exception.ChatNotFoundException;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import com.cybertech.farmcheck.service.exception.UserDeniedAccessException;
import com.cybertech.farmcheck.web.rest.errors.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    public FarmService farmService;
    public ChatService chatService;
    public UserService userService;

    @Autowired
    public ChatController(
        FarmService farmService,
        ChatService chatService,
        UserService userService
    ) {
        this.farmService = farmService;
        this.chatService = chatService;
        this.userService = userService;
    }

    /**
     * {@code GET /api/chats} : gives all the chats of a farm.
     *
     * @param farmId the id of the farm
     * @return the list of {@link List<ChatDTO>} with status 200
     * @throws FarmNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UnauthenticatedException  with status {@code 401 (UNAUTHORIZED)}
     * @throws UserDeniedAccessException with status {@code 401 (UNAUTHORIZED)}
     */
    @GetMapping
    public List<ChatDTO> getFarmChats(
        @RequestParam("farmId") Long farmId
    ) throws
        FarmNotFoundException,
        UnauthenticatedException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        return farmService
            .getFarmChats(farm.getId())
            .stream()
            .map(ChatDTO::new)
            .toList();
    }

    /**
     * {@code GET /api/chats/messages} : gives all the messages of a chat.
     *
     * @param chatId the id of the chat
     * @return the list of {@link List<MessageDTO>} with status 200
     * @throws ChatNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UnauthenticatedException  with status {@code 401 (UNAUTHORIZED)}
     * @throws UserDeniedAccessException with status {@code 401 (UNAUTHORIZED)}
     */
    @GetMapping("/messages")
    public List<MessageDTO> getChatMessages(
        @RequestParam("chatId") Long chatId
    ) throws
        ChatNotFoundException,
        UnauthenticatedException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Chat chat = chatService.getChat(chatId);

        farmService.checkUserAccess(chat.getFarm(), userLogin);

        return chatService
            .getChatMessages(chat.getId())
            .stream()
            .map(MessageDTO::new)
            .toList();
    }
}
