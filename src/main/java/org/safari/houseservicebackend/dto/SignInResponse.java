package org.safari.houseservicebackend.dto;

import org.safari.houseservicebackend.model.Role;

import java.util.List;

public record SignInResponse(
        String token,
        String username,
        List<String> roles
) {
}
