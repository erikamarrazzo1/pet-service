package it.assessment.pet.interfaces.rest.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.assessment.pet.application.service.PetService;
import it.assessment.pet.application.dto.PetDto;
import it.assessment.pet.interfaces.PathConfig;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(PathConfig.BASE_PATH_V1 + PathConfig.PET_PATH)
@Tag(name = "PET APIs")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Create a new pet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet created."),
            @ApiResponse(responseCode = "400", description = "Missing body."),
            @ApiResponse(responseCode = "500", description = "Error occurred creating new Pet.")
    })
    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto) {
        log.info("Received a request to create a pet {}...", petDto);
        PetDto result = petService.create(petDto);
        return ok().body(result);
    }

    @Operation(summary = "Update a pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated."),
            @ApiResponse(responseCode = "400", description = "Missing body."),
            @ApiResponse(responseCode = "500", description = "Error occurred updating existing Pet.")
    })
    @PatchMapping(PathConfig.ID_PATH)
    public ResponseEntity<PetDto> updatePet(@PathVariable("id") Long id,
                                            @RequestBody @Valid PetDto petDto) {
        log.info("Received a request to update a pet {}...", petDto);
        PetDto result = petService.update(id, petDto);
        return ok().body(result);
    }

    @Operation(summary = "Get a pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found."),
            @ApiResponse(responseCode = "404", description = "Pet not found."),
            @ApiResponse(responseCode = "500", description = "Error occurred retrieving Pet.")
    })
    @GetMapping(PathConfig.ID_PATH)
    public ResponseEntity<PetDto> getPetById(@PathVariable("id") Long id) {
        log.info("Received a request to retrieve a pet with ID: {}...", id);
        PetDto result = petService.getById(id);
        return ok().body(result);
    }

    @Operation(summary = "Get all pets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Pet."),
            @ApiResponse(responseCode = "500", description = "Error occurred retrieving list of Pet.")
    })
    @GetMapping()
    public ResponseEntity<List<PetDto>> getAllPets() {
        log.info("Received a request to retrieve all pets.");
        List<PetDto> result = petService.getAll();
        return ok().body(result);
    }

    @Operation(summary = "Delete pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted."),
            @ApiResponse(responseCode = "404", description = "Missing param.")
    })
    @DeleteMapping(PathConfig.ID_PATH)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Received a request to delete pet with ID: {}...", id);
        petService.delete(id);
        return noContent().build();
    }
}
