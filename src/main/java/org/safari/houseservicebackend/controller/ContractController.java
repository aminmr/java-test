package org.safari.houseservicebackend.controller;

import lombok.RequiredArgsConstructor;
import org.safari.houseservicebackend.dto.SignInResponse;
import org.safari.houseservicebackend.model.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contract")
@RequiredArgsConstructor
public class ContractController {

    @ResponseBody
    @GetMapping("hi")
    @Secured(value = "ROLE_ADMIN")
    public SignInResponse sayHi() {
        return new SignInResponse("token", "username", null);
    }
}
