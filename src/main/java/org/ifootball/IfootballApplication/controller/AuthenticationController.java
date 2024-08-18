package org.ifootball.IfootballApplication.controller;

import org.ifootball.IfootballApplication.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService service) {
        this.authenticationService = service;
    }

    @PostMapping("api/v1/login")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
}
