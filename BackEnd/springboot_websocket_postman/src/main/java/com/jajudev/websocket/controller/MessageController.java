package com.jajudev.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jajudev.websocket.entity.Message;
import com.jajudev.websocket.entity.MessageRequest;
import com.jajudev.websocket.service.MessageService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody MessageRequest messageRequest) {
        return messageService.saveMessage(messageRequest.getContent(), messageRequest.getSenderId(),
                messageRequest.getReceiverId());
    }
}