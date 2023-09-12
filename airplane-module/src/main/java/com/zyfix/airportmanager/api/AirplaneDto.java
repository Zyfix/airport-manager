package com.zyfix.airportmanager.api;



import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(title = "airplane data transfer object")
public record AirplaneDto (
        UUID id,
        String manufacturer,
        String model,
        Long capacity) {

}
