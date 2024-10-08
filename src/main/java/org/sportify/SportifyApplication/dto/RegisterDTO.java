package org.sportify.SportifyApplication.dto;

import org.sportify.SportifyApplication.enums.UserRolesEnum;

public record RegisterDTO(
        String first_name,
        String last_name,
        String login,
        String password,
        String role
) { }
