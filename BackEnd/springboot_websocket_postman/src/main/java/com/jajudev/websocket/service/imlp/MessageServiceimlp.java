package com.jajudev.websocket.service.imlp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jajudev.websocket.Repository.MessageRepository;
import com.jajudev.websocket.Repository.UserRepository;
import com.jajudev.websocket.entity.Message;
import com.jajudev.websocket.entity.User;
import com.jajudev.websocket.service.MessageService;

@Service
public class MessageServiceimlp implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Message saveMessage(Message Message) {
        return messageRepository.save(Message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message saveMessage(String content, Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = new Message(sender, receiver, content);
        return messageRepository.save(message);
    }
}
