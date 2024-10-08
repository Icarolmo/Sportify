package org.sportify.SportifyApplication.service;

import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.AuthenticationDTO;
import org.sportify.SportifyApplication.dto.RegisterDTO;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.repository.UserRepository;
import org.sportify.SportifyApplication.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    private final HashMap<String, String> acceptRoles = new HashMap<>(3) {{
       put("COMMON", "");
       put("ADMIN", "");
       put("ENTITY", "");
    }};

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public String login(AuthenticationDTO authDTO){
        validateAuthentication(authDTO);

        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User)auth.getPrincipal());
    }

    public void register(RegisterDTO registerDTO){
        validateRegister(registerDTO);

        if (repository.findByLogin(registerDTO.login()) != null){
            throw new RequestBodyWithIncorrectDataException("email de login não disponível");
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        var newUser = new User(registerDTO.first_name(), registerDTO.last_name() ,registerDTO.login(), encryptedPassword, registerDTO.role());
        repository.save(newUser);
    }

    private void validateAuthentication(AuthenticationDTO authenticationDTO){
        String errorMessage = "";
        boolean isValid = true;

        if(authenticationDTO.login().isBlank()){
            isValid = false;
            errorMessage += "login: o login não pode ser nulo ou vazio. ";
        }
        if(authenticationDTO.password().isBlank()){
            isValid = false;
            errorMessage += "password: a senha não pode ser nula ou vazia.";
        }

        if(!isValid){
            throw new RequestBodyWithIncorrectDataException(errorMessage);
        }
    }

    private void validateRegister(RegisterDTO registerDTO){
        String errorMessage = "";
        boolean isValid = true;

        if(registerDTO.first_name().isBlank()){
            isValid = false;
            errorMessage += "first_name: o primeiro nome do usuário não pode ser nulo ou vazio,";
        }
        if(registerDTO.last_name().isBlank()){
            isValid = false;
            errorMessage += "last_name: o ultimo nome do usuário não pode ser nulo ou vazio. ";
        }
        if(registerDTO.login().isBlank()){
            isValid = false;
            errorMessage += "login: o login não pode ser nulo ou vazio. ";
        }
        if(registerDTO.password().isBlank()){
            isValid = false;
            errorMessage += "password: a senha não pode ser nula ou vazia. ";
        }
        if(registerDTO.role().isBlank()){
            isValid = false;
            errorMessage += "role: papel do usuário não pode ser nulo ou vazio. ";
        }

        var emailAndSufix = registerDTO.login().split("@");
        if(emailAndSufix.length != 2 || !emailAndSufix[1].equals("usp.br")) {
            isValid = false;
            errorMessage += "email: o domínio do email não pertence a USP. ";
        }

        if(!acceptRoles.containsKey(registerDTO.role())){
            isValid = false;
            errorMessage += "role: tipo de role não permitida. ";
        }

        if(!isValid){
            throw new RequestBodyWithIncorrectDataException(errorMessage);
        }
    }
}
