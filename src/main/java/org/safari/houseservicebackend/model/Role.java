package org.safari.houseservicebackend.model;

public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    Role(String role_user) {

    }

    @Override
    public String toString() {
        return this.name();
    }
}
