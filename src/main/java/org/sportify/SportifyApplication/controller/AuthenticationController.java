package org.sportify.SportifyApplication.controller;


import jakarta.validation.Valid;
import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.AuthenticationDTO;
import org.sportify.SportifyApplication.dto.LoginResponseDTO;
import org.sportify.SportifyApplication.dto.RegisterDTO;
import org.sportify.SportifyApplication.repository.UserRepository;
import org.sportify.SportifyApplication.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.GenerateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO register){
        if (this.repository.findByLogin(register.login()) != null){
            return ResponseEntity.badRequest().build();
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(register.password());

        var newUser = new User(register.firstName(), register.lastName() ,register.login(), encryptedPassword, register.role());

        repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
