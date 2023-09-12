package com.zyfix.airportmanager.api;



import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;
import java.util.UUID;

@Schema
public record AirplaneDto (
        UUID id,
        String manufacturer,
        String model,
        Long capacity) {

}
