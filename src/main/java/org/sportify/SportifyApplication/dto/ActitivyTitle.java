package org.sportify.SportifyApplication.dto;

import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record ActitivyTitle(
        @NonNull
        @Size(min = 3, max = 20)
        String activity_title
) {}
