package com.salinas.test.usermanager.service.interfaces;

import java.util.List;

import com.salinas.test.usermanager.model.User;

public interface IUserService {
    User findUserById(Long id);

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(User user);

    User findUserByEmail(String email);

    Boolean alredyExistUser(String email);

    Boolean checkChangePassword(User user, String oldPassword);
}
