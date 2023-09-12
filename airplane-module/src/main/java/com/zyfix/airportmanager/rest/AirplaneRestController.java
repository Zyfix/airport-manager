package com.zyfix.airportmanager.rest;

import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.exception.handlers.AirplaneResponseExceptionHandler;
import com.zyfix.airportmanager.facade.AirplaneFacade;
import com.zyfix.airportmanager.exception.AirplaneNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/airplanes", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Airplane", description = "Airplane REST API")
public class AirplaneRestController {

    private final AirplaneFacade airplaneFacade;

    public AirplaneRestController(AirplaneFacade airplaneFacade) {
        this.airplaneFacade = airplaneFacade;
    }

    @Operation(summary = "Get airplane by Id", description = "Returns AirplaneDto by Id", tags = { "Airplane" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Airplane is returned successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
            @ApiResponse(responseCode = "404", description = "Airplane not found",
                    content = @Content(schema = @Schema(implementation = AirplaneNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AirplaneDto> getAirplaneById(
            @Parameter(
                    name = "id",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "ID of the airplane. Cannot be empty.",
                    example = "00000000-00000000-00000000-00000000"
            )
            @PathVariable UUID id
    ) {
        log.trace("Trying to get airplane by id: {}", id);
        AirplaneDto airplaneDto = airplaneFacade.findById(id).orElseThrow(AirplaneNotFoundException::new);
        log.trace("Successfully retrieved: ({}) {} {} with capacity {}",
                airplaneDto.id(),
                airplaneDto.manufacturer(),
                airplaneDto.model(),
                airplaneDto.capacity());
        return ResponseEntity.ok(airplaneDto);
    }

    @Operation(summary = "Get all airplanes", description = "Returns all airplanes", tags = { "Airplane" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Airplanes are returned successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
            @ApiResponse(responseCode = "404", description = "Airplanes not found",
                    content = @Content(schema = @Schema(implementation = AirplaneNotFoundException.class)))
    })
    @GetMapping( "/")
    public @ResponseBody ResponseEntity<List<AirplaneDto>> getAll() {
        log.trace("Listing all airplanes");
        List<AirplaneDto> airplaneDtos = airplaneFacade.findAll().orElseThrow(AirplaneNotFoundException::new);
        log.trace("Successfully retrieved all airplanes");
        return ResponseEntity.ok(airplaneDtos);
    }

    @Operation(summary = "Get all manufacturers", description = "Returns all manufacturers of airplanes in the db", tags = { "Airplane" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The manufacturers are returned successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class)))
    })
    @GetMapping("/manufacturers")
    public @ResponseBody ResponseEntity<List<String>> getAllManufacturers() {
        log.trace("Listing all manufacturers");
        List<String> manufacturers = airplaneFacade.findAllManufacturers();
        log.trace("Successfully retrieved all manufacturers");
        return ResponseEntity.ok(manufacturers);
    }

    @Operation(summary = "Get all airplanes from manufacturer", description = "Returns all airplanes made by specified manufacturer", tags = { "Airplane" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The airplanes are returned successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
            @ApiResponse(responseCode = "404", description = "Airplanes not found",
                    content = @Content(schema = @Schema(implementation = AirplaneNotFoundException.class)))
    })
    @GetMapping(value = "/manufacturers/{manufacturer}")
    public @ResponseBody ResponseEntity<List<AirplaneDto>> getAllAirplanesByManufacturer(
            @Parameter(
                    name = "manufacturer",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "Manufacturer of the airplane. Cannot be empty.",
                    example = "Boeing"
            )
            @PathVariable String manufacturer
    ) {
        log.trace("Listing all airplanes from manufacturer: {}", manufacturer);
        List<AirplaneDto> manufacturers = airplaneFacade.findAllByManufacturer(manufacturer).orElseThrow(AirplaneNotFoundException::new);
        log.trace("Successfully retrieved all airplanes");
        return ResponseEntity.ok(manufacturers);
    }

    // TODO refactor to accept AirplaneDto JSON not parameters
    @Operation(summary = "Create new airplane", description = "Creates new airplane", tags = { "Airplane" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The Airplane is created successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
            @ApiResponse(responseCode = "404", description = "The Airplane was not created",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))) // TODO return new custom Exception
    })
    @PostMapping( "/")
    public @ResponseBody ResponseEntity<AirplaneDto> create(
            @Parameter(name = "id",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The uuid of the airplane",
                    example = "e55f6639-32ef-4421-9b12-cd82c04f0928")
            @RequestParam(value = "id")
            String id,
            @Parameter(
                    name = "manufacturer",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "Manufacturer of the airplane. Cannot be empty.",
                    example = "Boeing"
            )
            @RequestParam(value = "manufacturer")
            String manufacturer,
            @Parameter(
                    name = "model",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "Model of the airplane. Tied to the manufacturer. Cannot be empty.",
                    example = "737"
            )
            @RequestParam(value = "model")
            String model,
            @Parameter(
                    name = "capacity",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "Capacity of the airplane. Cannot be empty.",
                    example = "200"
            )
            @RequestParam(value = "capacity")
            Long capacity
    ) {

        log.info("Creating new airplane: {} {} with capacity {}", manufacturer, model, capacity);

        UUID uuid = UUID.fromString(id);
        AirplaneDto airplaneDto = new AirplaneDto(uuid, manufacturer, model, capacity);

        AirplaneDto createdAirplane = airplaneFacade.create(airplaneDto)
                .orElseThrow(() -> new AirplaneNotFoundException("Could not create new airplane"));
        log.info("Successfully created new airplane: ({}) {} {} with capacity {}", createdAirplane, manufacturer, model, capacity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAirplane)
                .toUri();
        return ResponseEntity.created(location).body(createdAirplane);
    }

    @Operation(
            summary = "Returns airplanes sorted by specified field and sorting strategy",
            description = "Sort airplanes by their fields: manufacturer, model, capacity and strategies: asc, desc",
            tags = {"Airplane"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Airplane are returned successfully sorted by field",
                            content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
                    @ApiResponse(responseCode = "404", description = "Airplane not found",
                            content = @Content(schema = @Schema(implementation = AirplaneResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/sorted/{field}/{direction}")
    public @ResponseBody ResponseEntity<List<AirplaneDto>> getAirplanesSorted(
            @Parameter(description = "field of AirplaneDto", example = "capacity")
            @PathVariable String field,
            @Parameter(description = "sorting strategy: asc or desc", example = "asc | desc")
            @PathVariable String direction
    ) {
        return ResponseEntity.ok(airplaneFacade.findAllSortedBy(direction, field)
                .orElseThrow(AirplaneNotFoundException::new));
    }

    @Operation(
            summary = "Returns airplanes paged by offset and page size",
            description = "returns page of airplanes",
            tags = {"Airplane"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Airplanes are returned successfully paged by" +
                            " offset and size",
                            content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
                    @ApiResponse(responseCode = "404", description = "Airplane not found",
                            content = @Content(schema = @Schema(implementation = AirplaneResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/paged/{offset}/{size}")
    public @ResponseBody ResponseEntity<Page<AirplaneDto>> getAirplanesPaged(
            @PathVariable
            @Parameter(description = "offset of pagination", example = "0 | 2 | 10")
            int offset,
            @PathVariable
            @Parameter(description = "size of pagination", example = "10")
            int size
    ) {
        return ResponseEntity.ok(airplaneFacade.findAllPagedBy(offset, size)
                .orElseThrow(AirplaneNotFoundException::new));
    }

    @Operation(summary = "Delete Airplane", tags = {"Airplane"},
            description = "Deletes Airplane by Id and returns the deleted AirplaneDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Airplane was deleted successfully",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class))),
            @ApiResponse(responseCode = "404", description = "No Airplane was found",
                    content = @Content(schema = @Schema(implementation = AirplaneDto.class)))
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AirplaneDto> deleteAirplane(
            @PathVariable UUID id
    ) {
        log.trace("Deleting airplane by id: {}", id);
        var deletedDto = airplaneFacade.delete(id)
                .orElseThrow(AirplaneNotFoundException::new);
        log.trace("Successfully deleted airplane");
        return ResponseEntity.ok(deletedDto);
    }

    @PostMapping("/seed")
    public void seedDatabase() {
        log.trace("Seeding the airplane database");
        airplaneFacade.seed();
        log.trace("Airplane database seeded");
    }

    @DeleteMapping("/clear")
    public void clearDatabase() {
        log.trace("Clearing the airplane database");
        airplaneFacade.clear();
        log.trace("Airplane database cleared");
    }

}
