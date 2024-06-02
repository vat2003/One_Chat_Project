package com.jajudev.websocket.service;

import java.util.List;

import com.jajudev.websocket.entity.Message;

public interface MessageService {

  Message saveMessage(Message message);

  List<Message> getAllMessages();

  Message saveMessage(String content, Long senderId, Long receiverId);

}