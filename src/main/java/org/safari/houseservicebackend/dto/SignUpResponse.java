package org.safari.houseservicebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignUpResponse(
        @JsonProperty("username")
        String username,

        @JsonProperty("password")
        String password
) {
}
