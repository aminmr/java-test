package org.safari.houseservicebackend.controller;

import lombok.RequiredArgsConstructor;
import org.safari.houseservicebackend.config.JwtTokenUtil;
import org.safari.houseservicebackend.dto.SignInRequest;
import org.safari.houseservicebackend.dto.SignInResponse;
import org.safari.houseservicebackend.dto.SignUpRequest;
import org.safari.houseservicebackend.dto.SignUpResponse;
import org.safari.houseservicebackend.model.Role;
import org.safari.houseservicebackend.model.User;
import org.safari.houseservicebackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("sign-up")
    public SignUpResponse createUser(@RequestBody @Valid SignUpRequest request) {
        User savedUser = userService.save(request.toUser());
        return new SignUpResponse(
                savedUser.getUsername(),
                savedUser.getPassword()
        );
    }

    @PostMapping("sign-in")
    public ResponseEntity<SignInResponse> login(@RequestBody @Valid SignInRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        var user = (User) authenticate.getPrincipal();

        var response = new SignInResponse(
                jwtTokenUtil.generateAccessToken(user.getUsername()),
                user.getUsername(),
                user.getRoles()
        );

        return ResponseEntity.accepted().body(response);
    }
}
