package com.jajudev.websocket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jajudev.websocket.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}