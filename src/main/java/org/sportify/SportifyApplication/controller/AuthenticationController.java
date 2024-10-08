package org.sportify.SportifyApplication.controller;


import org.sportify.SportifyApplication.dto.AuthenticationDTO;
import org.sportify.SportifyApplication.dto.LoginResponseDTO;
import org.sportify.SportifyApplication.dto.RegisterDTO;
import org.sportify.SportifyApplication.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthorizationService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authDTO){
        var token = service.login(authDTO);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){
        service.register(registerDTO);
        return ResponseEntity.ok().body(registerDTO);
    }
}
