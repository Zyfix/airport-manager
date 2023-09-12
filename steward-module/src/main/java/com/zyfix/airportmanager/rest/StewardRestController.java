package com.zyfix.airportmanager.rest;

import com.zyfix.airportmanager.api.StewardDto;
import com.zyfix.airportmanager.exception.StewardNotFoundException;
import com.zyfix.airportmanager.exception.handlers.StewardResponseExceptionHandler;
import com.zyfix.airportmanager.facade.StewardFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/stewards", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Stewards", description = "Manage Stewards")
public class StewardRestController {

    private final StewardFacade stewardFacade;

    @Operation(summary = "Get Steward by Id", tags = {"Stewards"}, description = "Returns StewardDto by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Steward is returned successfully",
                    content = @Content(schema = @Schema(implementation = StewardDto.class))),
            @ApiResponse(responseCode = "404", description = "Steward not found",
                    content = @Content(schema = @Schema(implementation = StewardNotFoundException.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StewardDto> getStewardById(
            @Parameter(name = "id",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "The id of the Steward",
                    example = "00000000-00000000-00000000-00000000")
            @PathVariable UUID id
    ) {
        log.trace("Trying to get steward by id: {}", id);
        StewardDto stewardDto = stewardFacade.findById(id)
                .orElseThrow(StewardNotFoundException::new);
        log.trace("Successfully retrieved: {} {} {} {} {}",
                stewardDto.id(),
                stewardDto.firstName(),
                stewardDto.lastName(),
                stewardDto.dateOfBirth(),
                stewardDto.hireDate());
        return ResponseEntity.ok(stewardDto);
    }

    @Operation(summary = "Get Stewards", tags = {"Stewards"}, description = "Returns all Stewards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Stewards are returned successfully",
                    content = @Content(schema = @Schema(implementation = StewardDto.class))),
            @ApiResponse(responseCode = "404", description = "No Steward was found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StewardDto.class))))
    })
    @GetMapping("/")
    public @ResponseBody ResponseEntity<List<StewardDto>> getAll() {
        log.trace("Listing all stewards");
        List<StewardDto> dtoList = stewardFacade.findAll().orElseThrow(StewardNotFoundException::new);
        log.trace("Successfully listed all stewards");
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Create Steward", tags = {"Stewards"}, description = "Creates new Steward")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The Steward was created successfully",
                    content = @Content(schema = @Schema(implementation = StewardDto.class))),
            @ApiResponse(responseCode = "404", description = "Steward was not created",
                    content = @Content(schema = @Schema(implementation = StewardNotFoundException.class)))
    })
    @PostMapping( "/")
    public @ResponseBody ResponseEntity<StewardDto> createSteward(
            @Parameter(name = "id",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The uuid of the steward",
                    example = "e55f6639-32ef-4421-9b12-cd82c04f0928")
            @RequestParam(value = "id") UUID id,
            @Parameter(name = "firstName",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The first name of the Steward",
                    example = "Jan")
            @RequestParam(value = "firstName") String firstName,
            @Parameter(name = "lastName",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The lastName name of the Steward",
                    example = "Novák")
            @RequestParam(value = "lastName") String lastName,
            @Parameter(name = "dateOfBirth",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The birth date of Steward",
                    example = "1993-07-07")
            @RequestParam(value = "dateOfBirth")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
            @Parameter(name = "hireDate",
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "The hire date of Steward",
                    example = "2000-07-07")
            @RequestParam(value = "hireDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDate) {

        log.trace("Creating steward: firstName: {}, lastName: {}, dateOfBirth: {}, hireDate: {}",
                firstName, lastName, dateOfBirth, hireDate);

        StewardDto stewardDto = new StewardDto(id, firstName, lastName, dateOfBirth, hireDate);

        var createdStewardDto = stewardFacade.create(stewardDto)
                .orElseThrow(() -> new StewardNotFoundException("Could not create new steward"));

        log.trace("Successfully created steward with id: id:{}, firstName: {}, lastName: {}", id, firstName, lastName);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStewardDto)
                .toUri();
        return ResponseEntity.created(location).body(createdStewardDto);
    }

    @Operation(
            summary = "Returns stewards sorted by specified field and sorting strategy",
            description = "Sort stewards by their fields: firstName, lastName, dateOfBirth, hireDate and strategies: asc, desc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stewards are returned successfully sorted by field",
                            content = @Content(schema = @Schema(implementation = StewardDto.class))),
                    @ApiResponse(responseCode = "404", description = "Stewards not found",
                            content = @Content(schema = @Schema(implementation = StewardResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/sorted/{field}/{direction}")
    public @ResponseBody ResponseEntity<List<StewardDto>> getStewardsSorted(
            @PathVariable
            @Parameter(description = "field of StewardDto",
                    example = "firstName") String field,
            @PathVariable
            @Parameter(description = "sorting strategy: asc or desc",
                    example = "asc | desc") String direction
    ) {
        return ResponseEntity.ok(stewardFacade.findAllSortedBy(direction, field)
                .orElseThrow(StewardNotFoundException::new));
    }

    @Operation(
            summary = "Returns stewards paged by offset and page size",
            description = "returns page of stewards",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stewards are returned successfully paged by" +
                            " offset and size",
                            content = @Content(schema = @Schema(implementation = StewardDto.class))),
                    @ApiResponse(responseCode = "404", description = "Stewards not found",
                            content = @Content(schema = @Schema(implementation = StewardResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/paged/{offset}/{size}")
    public @ResponseBody ResponseEntity<Page<StewardDto>> getStewardsPaged(
            @PathVariable
            @Parameter(description = "offset of pagination",
                    example = "0 | 2 | 10") int offset,
            @PathVariable
            @Parameter(description = "size of pagination",
                    example = "10") int size
    ) {
        return ResponseEntity.ok(stewardFacade.findAllPagedBy(offset, size)
                .orElseThrow(StewardNotFoundException::new));
    }

    @Operation(
            summary = "Returns stewards born from date to date",
            description = "Returns list of stewards",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stewards are returned successfully born between from and to",
                            content = @Content(schema = @Schema(implementation = StewardDto.class))),
                    @ApiResponse(responseCode = "404", description = "Stewards not found",
                            content = @Content(schema = @Schema(implementation = StewardResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/born/{from}/{to}")
    public @ResponseBody ResponseEntity<List<StewardDto>> getStewardsBorn(
            @PathVariable
            @Parameter(description = "Date born from",
                    example = "1998-05-06")
            @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"yyyy", "yyyy/MM/dd", "dd.MM.yyyy"}) LocalDate from,
            @PathVariable(required = false)
            @Parameter(description = "Date born to",
                    example = "1998-05-06")
            @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"yyyy", "yyyy/MM/dd", "dd.MM.yyyy"}) LocalDate to
    ) {
        if (to == null) {
            to = LocalDate.now();
        }
        return ResponseEntity.ok(stewardFacade.findStewardsByBirthDate(from, to)
                .orElseThrow(StewardNotFoundException::new));
    }

    @Operation(
            summary = "Returns stewards hired from date to date",
            description = "Returns list of stewards",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stewards are returned successfully hired between from and to",
                            content = @Content(schema = @Schema(implementation = StewardDto.class))),
                    @ApiResponse(responseCode = "404", description = "Stewards not found",
                            content = @Content(schema = @Schema(implementation = StewardResponseExceptionHandler.APIError.class)))
            }
    )
    @GetMapping("/hired/{from}/{to}")
    public @ResponseBody ResponseEntity<List<StewardDto>> getStewardsHired(
            @PathVariable
            @Parameter(description = "Date hired from",
                    example = "1998-05-06") LocalDate from,
            @PathVariable(required = false)
            @Parameter(description = "Date hired to",
                    example = "1998-05-06") LocalDate to
    ) {
        if (to == null) {
            to = LocalDate.now();
        }
        return ResponseEntity.ok(stewardFacade.findStewardsByHireDate(from, to)
                .orElseThrow(StewardNotFoundException::new));
    }


    @Operation(summary = "Delete Stewards", tags = {"Stewards"},
            description = "Deletes Steward by Id and returns deleted DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Steward was deleted successfully",
                    content = @Content(schema = @Schema(implementation = StewardDto.class))),
            @ApiResponse(responseCode = "404", description = "No Steward was found",
                    content = @Content(schema = @Schema(implementation = StewardDto.class)))
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<StewardDto> deleteSteward(
            @PathVariable UUID id
    ) {
        log.trace("Deleting steward by id: {}", id);
        var deletedDto = stewardFacade.delete(id)
                .orElseThrow(StewardNotFoundException::new);
        log.trace("Successfully deleted steward");
        return ResponseEntity.ok(deletedDto);
    }

    @PostMapping("/seed")
    public void seedDatabase() {
        log.trace("Seeding the database");
        stewardFacade.seed();
        log.trace("Database seeded");
    }

    @DeleteMapping("/clear")
    public void clearDatabase() {
        log.trace("Clearing the database");
        stewardFacade.clear();
        log.trace("Database cleared");
    }

}
