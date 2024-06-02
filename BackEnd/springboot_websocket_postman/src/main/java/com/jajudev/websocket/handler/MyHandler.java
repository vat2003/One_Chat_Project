package com.jajudev.websocket.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jajudev.websocket.service.MessageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler {

    @Getter
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private MessageService messageService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("Received message: {}", message.getPayload());

        if (!sessions.contains(session)) {
            sessions.add(session);
        }

        String[] parts = message.getPayload().split(",", 3);
        Long senderId = Long.parseLong(parts[0]);
        Long receiverId = Long.parseLong(parts[1]);
        String content = parts[2];

        messageService.saveMessage(content, senderId, receiverId);

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession != null && webSocketSession.isOpen()
                    && !webSocketSession.getId().equals(session.getId())) {
                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }
}
