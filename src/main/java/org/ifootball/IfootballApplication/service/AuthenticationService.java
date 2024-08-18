package org.ifootball.IfootballApplication.service;

import org.ifootball.IfootballApplication.security.JwtService;
import org.springframework.security.core.Authentication;

public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generationToken(authentication);
    }
}
