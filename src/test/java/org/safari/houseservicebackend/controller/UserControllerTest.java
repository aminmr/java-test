package org.safari.houseservicebackend.controller;

import org.junit.jupiter.api.Test;
import org.safari.houseservicebackend.exception.RecordNotFoundException;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void singInTest_ShouldBeOk() throws Exception {
        given(userService.findByUsername(anyString())).willReturn(new User());

        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("ali"));
    }

    @Test
    void singInTest_ShouldBeFailed() throws Exception {
        given(userService.findByUsername(anyString())).willThrow(RecordNotFoundException.class);

        mockMvc.perform(get("/auth/login")).andExpect(status().isNotFound());
    }
}
