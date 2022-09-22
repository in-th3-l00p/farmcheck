package com.cybertech.farmcheck.web.websocket;

import com.cybertech.farmcheck.domain.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FarmChatController {

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/{farmId}")
    public Message sendMessage(
        @DestinationVariable("farmId") Long farmId,
        @Payload Message message
    ) {
        return message;
    }
}
