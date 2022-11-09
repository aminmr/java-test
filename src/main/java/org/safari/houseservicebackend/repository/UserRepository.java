package org.safari.houseservicebackend.repository;

import org.safari.houseservicebackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameAndDeletedAtIsNull(String username);
}
