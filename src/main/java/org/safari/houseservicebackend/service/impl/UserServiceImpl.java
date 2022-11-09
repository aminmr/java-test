package org.safari.houseservicebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.safari.houseservicebackend.exception.RecordNotFoundException;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.repository.UserRepository;
import org.safari.houseservicebackend.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        return repository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @CachePut(value = "user", key = "#username")
    public void changePassword(String username, String password) {
        // todo implement me
    }

    @Override
    @CacheEvict(value = "user", key = "#username")
    public void deleteByUsername(String username) {
        // todo implement me
    }
}
