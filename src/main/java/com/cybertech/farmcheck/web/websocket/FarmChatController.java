package com.cybertech.farmcheck.web.websocket;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.Message;
import com.cybertech.farmcheck.service.FarmService;
import com.cybertech.farmcheck.service.dto.MessageDTO;
import com.cybertech.farmcheck.service.exception.FarmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FarmChatController {
    FarmService farmService;

    @Autowired
    public FarmChatController(FarmService farmService) {
        this.farmService = farmService;
    }

    @MessageMapping("/chat/sendMessage/{farmId}")
    @SendTo("/topic/{farmId}")
    public MessageDTO sendMessage(
        @DestinationVariable("farmId") Long farmId,
        @Payload MessageDTO messageDTO
    ) throws FarmNotFoundException {
        Farm farm = farmService.getFarm(farmId);
        return new MessageDTO(
            farmService.addMessage(
                new Message(messageDTO, farm)
            )
        );
    }
}
