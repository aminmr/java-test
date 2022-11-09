package org.safari.houseservicebackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "username", unique = true, nullable = false, updatable = false)
    @NotBlank(message = "username is required")
    private String username;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "password is required")
    private String password;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // relations
    @ElementCollection
    @CollectionTable(
            name = "user_role",
            joinColumns=@JoinColumn(name = "username", referencedColumnName = "username")
    )
    @Column(name="role")
    private List<String> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Profile profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles.stream().map(Enum::name).collect(Collectors.toList());
    }

    // implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return deletedAt == null;
    }
}
