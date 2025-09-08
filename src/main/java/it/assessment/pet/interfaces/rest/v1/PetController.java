package it.assessment.pet.interfaces.rest.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.assessment.pet.application.dto.PetRequestDto;
import it.assessment.pet.application.service.PetService;
import it.assessment.pet.application.dto.PetResponseDto;
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
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid PetRequestDto petRequestDto) {
        log.info("Received a request to create a pet {}...", petRequestDto);
        PetResponseDto result = petService.create(petRequestDto);
        return ok().body(result);
    }

    @Operation(summary = "Update a pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated."),
            @ApiResponse(responseCode = "400", description = "Missing body."),
            @ApiResponse(responseCode = "500", description = "Error occurred updating existing Pet.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDto> updatePet(@PathVariable("id") Long id,
                                                    @RequestBody @Valid PetRequestDto petRequestDto) {
        log.info("Received a request to update a pet {}...", petRequestDto);
        PetResponseDto result = petService.update(id, petRequestDto);
        return ok().body(result);
    }

    @Operation(summary = "Get a pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found."),
            @ApiResponse(responseCode = "404", description = "Pet not found."),
            @ApiResponse(responseCode = "500", description = "Error occurred retrieving Pet.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPetById(@PathVariable("id") Long id) {
        log.info("Received a request to retrieve a pet with ID: {}...", id);
        PetResponseDto result = petService.getById(id);
        return ok().body(result);
    }

    @Operation(summary = "Get all pets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Pet."),
            @ApiResponse(responseCode = "500", description = "Error occurred retrieving list of Pet.")
    })
    @GetMapping()
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        log.info("Received a request to retrieve all pets.");
        List<PetResponseDto> result = petService.getAll();
        return ok().body(result);
    }

    @Operation(summary = "Delete pet by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted."),
            @ApiResponse(responseCode = "404", description = "Missing param.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Received a request to delete pet with ID: {}...", id);
        petService.delete(id);
        return noContent().build();
    }
}
