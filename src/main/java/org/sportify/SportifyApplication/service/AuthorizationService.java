package org.sportify.SportifyApplication.service;

import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.RegisterDTO;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    private final HashMap<String, String> acceptRoles = new HashMap<>(3) {{
       put("COMMON", "");
       put("ADMIN", "");
       put("ENTITY", "");
    }};

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public void register(RegisterDTO registerDTO){
        validateRegister(registerDTO);

        if (repository.findByEmail(registerDTO.email()) != null){
            throw new RequestBodyWithIncorrectDataException("email de email não disponível");
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        var newUser = new User(registerDTO.first_name(), registerDTO.last_name() ,registerDTO.email(), encryptedPassword, registerDTO.role());
        repository.save(newUser);
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
        if(registerDTO.email().isBlank()){
            isValid = false;
            errorMessage += "email: o email não pode ser nulo ou vazio. ";
        }
        if(registerDTO.password().isBlank()){
            isValid = false;
            errorMessage += "password: a senha não pode ser nula ou vazia. ";
        }
        if(registerDTO.role().isBlank()){
            isValid = false;
            errorMessage += "role: papel do usuário não pode ser nulo ou vazio. ";
        }

        var emailAndSufix = registerDTO.email().split("@");
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
