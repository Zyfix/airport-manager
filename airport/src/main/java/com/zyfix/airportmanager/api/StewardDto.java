package com.zyfix.airportmanager.api;



import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

@Schema
public record StewardDto(
        UUID id,
        String firstName,
        String lastName,
        @Schema(type = "string", format = "date",
                description = "birthdate of steward",
                example = "2022-12-22")
        LocalDate dateOfBirth,
        @Schema(type = "string", format = "date",
                description = "hire date of steward",
                example = "2022-12-22")
        LocalDate hireDate) {
}
