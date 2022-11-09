package org.safari.houseservicebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public record SignInRequest(
        @Size(min = 3, message = "username must longer than 3 character")
        @JsonProperty("username") String username,
        @Size(min = 5, message = "Password must contain at least 5 characters")
        @JsonProperty("password") String password
) {
}
