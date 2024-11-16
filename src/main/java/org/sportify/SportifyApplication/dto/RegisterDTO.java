package org.sportify.SportifyApplication.dto;

public record RegisterDTO(
        String first_name,
        String last_name,
        String email,
        String password,
        String role
) { }
