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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * {@code POST /api/chats} : used for adding a new chat to a farm.
     *
     * @param farmId  the farm's id
     * @param chatDTO the sensor data transfer object
     * @return message status, with status {@code 201 (CREATED)}
     * @throws UnauthenticatedException  with status {@code 401 (NOT AUTHORIZED)}
     * @throws FarmNotFoundException     with status {@code 404 (NOT FOUND)}
     * @throws UserDeniedAccessException with status {@code 401 (NOT AUTHORIZED)}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addChat(
        @RequestParam("farmId") Long farmId,
        @RequestBody ChatDTO chatDTO
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        chatService.addChat(farm, chatDTO);

        return "Chat added";
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
     * {@code DELETE /api/chats} : deletes a chat of a farm.
     *
     * @param farmId the id of the farm
     * @param chatId the id of the chat
     * @return the confirmation of the operation {@link ResponseEntity<String>} with status 200
     * @throws UnauthenticatedException  with status {@code 404 (NOT FOUND)}
     * @throws FarmNotFoundException  with status {@code 401 (UNAUTHORIZED)}
     * @throws UserDeniedAccessException with status {@code 401 (UNAUTHORIZED)}
     * @throws ChatNotFoundException with status {@code 401 (UNAUTHORIZED)}
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFarmChat(
        @RequestParam("farmId") Long farmId,
        @RequestParam("chatId") Long chatId
    ) throws
        UnauthenticatedException,
        FarmNotFoundException,
        UserDeniedAccessException,
        ChatNotFoundException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(UnauthenticatedException::new);

        Farm farm = farmService.getFarm(farmId);
        farmService.checkUserAccess(farm, userLogin);

        Chat chat = chatService.getChat(chatId);

        chatService.deleteChat(chat);
        return ResponseEntity.ok("Chat deleted");
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
