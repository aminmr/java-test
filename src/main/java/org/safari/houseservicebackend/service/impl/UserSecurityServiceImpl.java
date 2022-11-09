package org.safari.houseservicebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.safari.houseservicebackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
    }
}
