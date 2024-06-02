package com.jajudev.websocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 255)
    private String userName;

    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String fullName;

    @Column(length = 255)
    private String avatarUrl;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean active = false;
}