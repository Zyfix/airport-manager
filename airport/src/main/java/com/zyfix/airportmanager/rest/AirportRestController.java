package com.zyfix.airportmanager.rest;

import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.api.StewardDto;
import com.zyfix.airportmanager.facade.AirportFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/airport", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Airport", description = "Manage Airport")
public class AirportRestController {


    private AirportFacade airportFacade;

    @Autowired
    public AirportRestController(AirportFacade airportFacade) {
        this.airportFacade = airportFacade;
    }

    @GetMapping("/airplanes")
    public @ResponseBody ResponseEntity<AirplaneDto[]> getAllAirplanes() {
        AirplaneDto[] airplanes = airportFacade.getAirplanes();

        return ResponseEntity.ok(airplanes);
    }

    @GetMapping("/stewards")
    public @ResponseBody ResponseEntity<StewardDto[]> getAllStewards() {
        StewardDto[] stewards = airportFacade.getStewards();

        return ResponseEntity.ok(stewards);
    }

    @PostMapping("/airplanes")
    public @ResponseBody ResponseEntity<AirplaneDto> createAirplaneJson(@RequestBody AirplaneDto airplaneDto) {
        AirplaneDto airplane = airportFacade.createAirplane(airplaneDto);

        return ResponseEntity.ok(airplane);
    }

    @PostMapping(value = "/airplanes", params = {"id", "manufacturer", "model", "capacity"})
    public @ResponseBody ResponseEntity<AirplaneDto> createAirplaneQuery(@RequestParam UUID id, @RequestParam String manufacturer, @RequestParam String model, @RequestParam Long capacity) {
        AirplaneDto airplaneDto = new AirplaneDto(id, manufacturer, model, capacity);
        AirplaneDto airplane = airportFacade.createAirplane(airplaneDto);

        return ResponseEntity.ok(airplane);
    }

    @PostMapping("/stewards")
    public @ResponseBody ResponseEntity<StewardDto> createSteward(@RequestBody StewardDto stewardDto) {
        StewardDto steward = airportFacade.createSteward(stewardDto);

        return ResponseEntity.ok(steward);
    }

    @PostMapping(value = "/stewards", params = {"id", "firstName", "lastName", "birthDate", "hireDate"})
    public @ResponseBody ResponseEntity<StewardDto> createStewardQuery(@RequestParam UUID id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam LocalDate birthDate, @RequestParam LocalDate hireDate) {
        StewardDto stewardDto = new StewardDto(id, firstName, lastName, birthDate, hireDate);
        StewardDto steward = airportFacade.createSteward(stewardDto);

        return ResponseEntity.ok(steward);
    }


}
