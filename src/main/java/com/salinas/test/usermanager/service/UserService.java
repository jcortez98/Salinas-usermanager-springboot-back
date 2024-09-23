package com.salinas.test.usermanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.repository.UserRepository;
import com.salinas.test.usermanager.service.interfaces.IUserService;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Override
    public Boolean alredyExistUser(String email) {
        User user = findUserByEmail(email);
        return getAllUsers().contains(user);
    }

    public Boolean alredyExistUser(String email, Long id) {
        User user = findUserByEmail(email);
        return getAllUsers().contains(user) && user.getId() != id;
    }

    @Override
    public Boolean checkChangePassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

}
