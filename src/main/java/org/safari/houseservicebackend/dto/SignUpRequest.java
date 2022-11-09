package org.safari.houseservicebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.safari.houseservicebackend.model.Profile;
import org.safari.houseservicebackend.model.User;

import javax.validation.constraints.Size;

public record SignUpRequest(
        @Size(min = 1, message = "Firstname is required")
        @JsonProperty("firstname")
        String firstname,

        @Size(min = 1, message = "Lastname is required")
        @JsonProperty("lastname")
        String lastname,

        @JsonProperty("company")
        String company,

        @Size(min = 3, message = "Username must longer than 3 character")
        @JsonProperty("username")
        String username,

        @Size(min = 5, message = "Password must contain at least 5 characters")
        @JsonProperty("password")
        String password
) {
    public User toUser() {
        User user = new User(this.username, this.password);

        Profile profile = new Profile();
        profile.setFirstname(this.firstname);
        profile.setLastname(this.lastname);
        profile.setCompany(this.company);

        user.setProfile(profile);

        return user;
    }
}
