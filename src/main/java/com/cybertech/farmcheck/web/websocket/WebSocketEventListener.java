package com.cybertech.farmcheck.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(
        SimpMessageSendingOperations messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("New web socket connection.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("Web socket disconnected.");
    }
}
