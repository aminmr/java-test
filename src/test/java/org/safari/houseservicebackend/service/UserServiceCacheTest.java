package org.safari.houseservicebackend.service;

import org.junit.jupiter.api.Test;
import org.safari.houseservicebackend.model.Profile;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceCacheTest {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    @Test
    void findByIdTest_withCache() {
        var user = getSimpleUser();

        var username = "testUser";
        given(repository.findByUsernameAndDeletedAtIsNull(username)).willReturn(Optional.of(user));

        service.findByUsername(username);
        service.findByUsername(username);
        service.findByUsername(username);

        then(repository).should(times(1)).findByUsernameAndDeletedAtIsNull(username);
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
