package com.project.shopapp.Service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.social.facebook.api.Account;
import org.springframework.stereotype.Service;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.Service.UserService;
import com.project.shopapp.entity.User;
import com.project.shopapp.repository.UserDAO;
import com.project.shopapp.security.DataNotFoundException;
import com.project.shopapp.security.JwtTokenUtil;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImlp implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDAO UserDAO;

    @Override
    public List<User> getAllUser() {
        return UserDAO.findAll();
    }

    @Override
    public String login(String mail, String password) throws Exception {

        Optional<User> optionalUser = UserDAO.findByEmail(mail);

        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        User existingUser = optionalUser.get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                mail, password,
                existingUser.getAuthorities());

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);

    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String Email = jwtTokenUtil.extractEmail(token);
        Optional<User> user = UserDAO.findByEmail(Email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Transactional
    public User updateAccount(Long id, UpdateUserDTO updateUserDTO) {
        User existingUser = UserDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String mail = updateUserDTO.getEmail();
        if (!existingUser.getEmail().equals(mail) && UserDAO.existsByEmail(mail)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (updateUserDTO.getFullName() != null) {
            existingUser.setFullName(updateUserDTO.getFullName());
        }

        if (updateUserDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }

        if (updateUserDTO.getAvatarUrl() != null) {
            existingUser.setAvatarUrl(updateUserDTO.getAvatarUrl());
        }

        existingUser.setActive(updateUserDTO.isActive());

        return UserDAO.save(existingUser);

    }

}