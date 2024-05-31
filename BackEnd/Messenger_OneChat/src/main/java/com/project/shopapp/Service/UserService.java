package com.project.shopapp.Service;

import java.util.List;

import org.springframework.social.facebook.api.Account;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.entity.User;

public interface UserService {
    List<User> getAllUser();

    String login(String mail, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User updateAccount(Long id, UpdateUserDTO updateUserDTO);
}
