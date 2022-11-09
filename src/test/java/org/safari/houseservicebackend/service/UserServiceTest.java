package org.safari.houseservicebackend.service;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.safari.houseservicebackend.exception.RecordNotFoundException;
import org.safari.houseservicebackend.model.Profile;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.repository.UserRepository;
import org.safari.houseservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Find user by username and deletedAt is null should be ok")
    void findByUsernameAndDeletedAtIsNull_OK() {
        var user = getSimpleUser();
        User savedUser = repository.save(user);
        User fetchedUser = service.findByUsername("testUser");

        then(fetchedUser.getUsername()).isEqualTo(savedUser.getUsername());
    }

    @Test
    @DisplayName("Find user by username and deletedAt is null should be failed")
    void findByUsernameAndDeletedAtIsNull_ThrowsException() {
        var username = "testUsername";
        Throwable throwable = catchThrowable(() -> service.findByUsername(username));
        BDDAssertions.then(throwable).isInstanceOf(RecordNotFoundException.class);
    }

    private User getSimpleUser() {
        User user = new User();

        user.setUsername("testUser");
        user.setPassword("testPass");

        Profile profile = new Profile();
        profile.setFirstname("firstname");
        profile.setLastname("lastname");

        user.setProfile(profile);

        return user;
    }


}
