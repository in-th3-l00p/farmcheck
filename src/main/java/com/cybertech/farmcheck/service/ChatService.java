package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Chat;
import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Message;
import com.cybertech.farmcheck.repository.ChatRepository;
import com.cybertech.farmcheck.repository.MessageRepository;
import com.cybertech.farmcheck.service.dto.ChatDTO;
import com.cybertech.farmcheck.service.exception.ChatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private ChatRepository chatRepository;

    private MessageRepository messageRepository;

    @Autowired
    public ChatService(
        ChatRepository chatRepository,
        MessageRepository messageRepository
    ) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Adds a new chat to a farm.
     *
     * @param farm    farm entity
     * @param chatDTO the dto of the chat
     */
    public void addChat(Farm farm, ChatDTO chatDTO) {
        Chat chat = new Chat(chatDTO);
        chat.setFarm(farm);
        chatRepository.save(chat);
    }

    /**
     * Gets a {@link Chat}.
     *
     * @param chatId chat's id
     * @return the Chat object if found
     * @throws ChatNotFoundException if the chat doesn't exist
     */
    public Chat getChat(Long chatId) throws ChatNotFoundException {
        return chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    /**
     * Adds a message to a chat.
     *
     * @param message the message object
     * @return the {@link Message} saved in the db
     */
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Gets all the messages of a chat
     *
     * @param chatId the id of the chat
     * @return the {@link List <Message>} sorted by their send date
     */
    public List<Message> getChatMessages(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    /**
     * Removes the given chat from the db.
     *
     * @param chat the chat entity
     */
    public void deleteChat(Chat chat) {
        for (Message message : chat.getMessages())
            messageRepository.delete(message);
        chatRepository.delete(chat);
    }
}
