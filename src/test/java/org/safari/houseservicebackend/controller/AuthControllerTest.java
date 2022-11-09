package org.safari.houseservicebackend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safari.houseservicebackend.model.Role;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.service.UserService;
import org.safari.houseservicebackend.service.impl.UserSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;
    @MockBean
    private UserSecurityServiceImpl userSecurityService;

    @Nested
    @DisplayName("AuthControllerTest SignUp")
    class AuthControllerTest_SignUp {
        @Test
        void ok() throws Exception {
            var returnUser = new User();
            returnUser.setUsername("test-user");
            returnUser.setPassword("123456789");

            given(userService.save(any())).willReturn(returnUser);

            mockMvc.perform(
                            post("/auth/sign-up")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                                "firstname": "ronin",
                                                "lastname": "hyper",
                                                "company": "none",
                                                "username": "test-user",
                                                "password": "123456789"
                                            }
                                            """)
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("username").value("test-user"))
                    .andExpect(jsonPath("password").value("123456789"));
        }

        @Test
        void failed_ProfileIsRequired() throws Exception {
            mockMvc.perform(
                            post("/auth/sign-up")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                                "firstname": "",
                                                "lastname": "",
                                                "company": "none",
                                                "username": "test-user",
                                                "password": "123456789"
                                            }
                                            """)
                    ).andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("firstname").value("Firstname is required"))
                    .andExpect(jsonPath("lastname").value("Lastname is required"));
        }
    }

    @Nested
    @DisplayName("AuthControllerTest SignIn")
    class AuthControllerTest_SignIn {
        @Test
        void getToken_ok() throws Exception {
            given(userSecurityService
                    .loadUserByUsername("test-user"))
                    .willReturn(
                            new User("test-user",
                                    passwordEncoder.encode("123456789"),
                                    List.of(Role.USER)));

            mockMvc.perform(
                            post("/auth/sign-in")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                                "username": "test-user",
                                                "password": "123456789"
                                            }
                                            """)
                    ).andDo(print())
                    .andExpect(status().isAccepted())
                    .andExpect(jsonPath("token").isString())
                    .andExpect(jsonPath("username").value("test-user"))
                    .andExpect(jsonPath("roles[0]").value("USER"));
        }

        @Test
        void getToken_NotAuthorized() throws Exception {
            given(userSecurityService
                    .loadUserByUsername("test-user"))
                    .willThrow(UsernameNotFoundException.class);

            mockMvc.perform(
                            post("/auth/sign-in")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                                "username": "test-user",
                                                "password": "123456789"
                                            }
                                            """)
                    ).andDo(print())
                    .andExpect(status().isUnauthorized());
        }
    }


}