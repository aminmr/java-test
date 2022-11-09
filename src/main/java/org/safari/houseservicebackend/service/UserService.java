package org.safari.houseservicebackend.service;

import org.safari.houseservicebackend.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
    void changePassword(String username, String password);
    void deleteByUsername(String username);
}
