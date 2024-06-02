package com.jajudev.websocket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jajudev.websocket.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
