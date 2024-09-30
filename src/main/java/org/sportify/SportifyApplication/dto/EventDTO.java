package org.sportify.SportifyApplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record EventDTO(
        @NonNull
        @Size(min = 3, max = 20)
        String activity_title,
        @NonNull
        String type,
        @Size(min = 0, max = 150)
        String description,
        @NonNull
        String localization,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @NonNull
        LocalDate date,
        @JsonFormat(pattern = "HH:mm:ss")
        @NonNull
        LocalTime start_hour,
        @JsonFormat(pattern = "HH:mm:ss")
        @NonNull
        LocalTime end_hour,
        @NonNull
        String privacy,
        @NonNull
        int number_of_person
){};