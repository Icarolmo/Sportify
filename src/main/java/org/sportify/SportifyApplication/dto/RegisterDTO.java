package org.sportify.SportifyApplication.dto;

import org.sportify.SportifyApplication.enums.UserRolesEnum;

public record RegisterDTO(
        String firstName,
        String lastName,
        String login,
        String password,
        UserRolesEnum role
) { }
