package it.assessment.pet.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.assessment.pet.application.dto.PetDto;
import it.assessment.pet.application.service.PetService;
import it.assessment.pet.domain.pet.exception.InvalidPetAgeExceptionPet;
import it.assessment.pet.domain.pet.exception.PetNotFoundExceptionPet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import it.assessment.pet.interfaces.rest.v1.PetController;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private PetDto dto1;
    private PetDto dto2;

    @BeforeEach
    void setUp() {
        dto1 = new PetDto();
        dto1.setId(1L);
        dto1.setName("Fido");
        dto1.setSpecies(PetSpecies.DOG.name());
        dto1.setAge(1);
        dto1.setOwnerName("Mario Rossi");

        dto2 = new PetDto();
        dto2.setId(2L);
        dto2.setName("Milo");
        dto2.setSpecies(PetSpecies.CAT.name());
        dto2.setAge(1);
        dto2.setOwnerName("Mario Rossi");
    }

    @Test
    void test_createPet_ok() throws Exception {
        when(petService.create(any(PetDto.class))).thenReturn(dto1);

        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fido"))
                .andExpect(jsonPath("$.species").value(PetSpecies.DOG.name()))
                .andExpect(jsonPath("$.age").value(1))
                .andExpect(jsonPath("$.ownerName").value("Mario Rossi"));

        verify(petService).create(any(PetDto.class));
    }

    @Test
    void test_createPet_missingBody() throws Exception {
        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(petService);
    }

    @Test
    void test_createPet_internalServerError() {
        when(petService.create(any(PetDto.class))).thenThrow(new RuntimeException());

        assertThrows(ServletException.class, () -> mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isInternalServerError()));

        verify(petService).create(any(PetDto.class));
    }

    @Test
    void test_createPet_invalidInputAge() throws Exception {
        when(petService.create(any(PetDto.class))).thenThrow(new InvalidPetAgeExceptionPet(-1));

        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void test_getPetById_ok() throws Exception {
        when(petService.getById(1L)).thenReturn(dto1);

        mockMvc.perform(get("/api/v1/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fido"))
                .andExpect(jsonPath("$.species").value(PetSpecies.DOG.name()))
                .andExpect(jsonPath("$.age").value(1))
                .andExpect(jsonPath("$.ownerName").value("Mario Rossi"));

        verify(petService).getById(1L);
    }

    @Test
    void test_getPetById_notFound() throws Exception {
        when(petService.getById(1L)).thenThrow(new PetNotFoundExceptionPet(1L));

        mockMvc.perform(get("/api/v1/pets/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Pet with id: 1 not found."));

        verify(petService).getById(1L);
    }

    @Test
    void test_getPetById_internalServerError() {
        when(petService.getById(any())).thenThrow(new RuntimeException());

        assertThrows(ServletException.class, () -> mockMvc.perform(get("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isInternalServerError()));

        verify(petService).getById(anyLong());
    }

    @Test
    void test_updatePet_ok() throws Exception {
        PetDto updatedDto = new PetDto();
        updatedDto.setId(1L);
        updatedDto.setName("Fido Updated");
        updatedDto.setSpecies(PetSpecies.DOG.name());
        updatedDto.setAge(1);
        updatedDto.setOwnerName("Mario Rossi");

        when(petService.update(eq(1L), any(PetDto.class))).thenReturn(updatedDto);

        mockMvc.perform(patch("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fido Updated"))
                .andExpect(jsonPath("$.species").value(PetSpecies.DOG.name()))
                .andExpect(jsonPath("$.age").value(1))
                .andExpect(jsonPath("$.ownerName").value("Mario Rossi"));

        verify(petService).update(eq(1L), any(PetDto.class));
    }

    @Test
    void test_updatePet_missingBody() throws Exception {
        mockMvc.perform(patch("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(petService);
    }

    @Test
    void test_deletePetById_noContent() throws Exception {
        doNothing().when(petService).delete(1L);

        mockMvc.perform(delete("/api/v1/pets/1"))
                .andExpect(status().isNoContent());

        verify(petService).delete(1L);
    }

    @Test
    void test_getAllPets_ok() throws Exception {
        when(petService.getAll()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/api/v1/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fido"))
                .andExpect(jsonPath("$[1].name").value("Milo"));

        verify(petService).getAll();
    }
}

