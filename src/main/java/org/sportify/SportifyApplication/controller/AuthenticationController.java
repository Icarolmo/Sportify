package org.sportify.SportifyApplication.controller;


import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.AuthenticationDTO;
import org.sportify.SportifyApplication.dto.LoginResponseDTO;
import org.sportify.SportifyApplication.dto.RegisterDTO;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.security.TokenService;
import org.sportify.SportifyApplication.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authDTO){
        validateAuthentication(authDTO);

        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

         var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){
        service.register(registerDTO);
        return ResponseEntity.ok().body(registerDTO);
    }

    private void validateAuthentication(AuthenticationDTO authenticationDTO){
        String errorMessage = "";
        boolean isValid = true;

        if(authenticationDTO.email().isBlank()){
            isValid = false;
            errorMessage += "email: o email não pode ser nulo ou vazio. ";
        }
        if(authenticationDTO.password().isBlank()){
            isValid = false;
            errorMessage += "password: a senha não pode ser nula ou vazia.";
        }

        if(!isValid){
            throw new RequestBodyWithIncorrectDataException(errorMessage);
        }
    }
}
