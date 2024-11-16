package org.sportify.SportifyApplication.dto;

public record AuthenticationDTO(
        String email,
        String password
) { }
