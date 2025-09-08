package it.assessment.pet.application.service;

import it.assessment.pet.application.dto.PetRequestDto;
import it.assessment.pet.application.dto.PetResponseDto;
import it.assessment.pet.domain.pet.exception.PetNotFoundExceptionPet;
import it.assessment.pet.domain.pet.mapper.PetMapper;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import it.assessment.pet.domain.pet.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    private PetRepository petRepository;
    private PetMapper petMapper;
    private PetService petService;

    private PetResponseDto petResponseDto;
    private PetRequestDto petRequestDto;
    private Pet petDomain;

    @BeforeEach
    void setUp() {
        petRepository = mock(PetRepository.class);
        petMapper = mock(PetMapper.class);
        petService = new PetService(petRepository, petMapper);

        petResponseDto = new PetResponseDto();
        petResponseDto.setId(1L);
        petResponseDto.setName("Fido");
        petResponseDto.setSpecies("DOG");
        petResponseDto.setAge(1);
        petResponseDto.setOwnerName("Mario Rossi");

        petRequestDto = new PetRequestDto();
        petRequestDto.setName("Fido");
        petRequestDto.setSpecies("DOG");
        petRequestDto.setAge(1);
        petRequestDto.setOwnerName("Mario Rossi");

        petDomain = new Pet();
        petDomain.setId(1L);
        petDomain.setName("Fido");
        petDomain.setSpecies(PetSpecies.DOG);
        petDomain.setAge(1);
        petDomain.setOwnerName("Mario Rossi");
    }

    @Test
    void test_createPet_successfully() {
        when(petMapper.createFromRequest(petRequestDto)).thenReturn(petDomain);
        petDomain.setId(1L);
        when(petRepository.save(petDomain)).thenReturn(petDomain);
        petResponseDto.setId(petDomain.getId());
        when(petMapper.toDto(petDomain)).thenReturn(petResponseDto);

        PetResponseDto result = petService.create(petRequestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fido", result.getName());
        assertEquals("DOG", result.getSpecies());
        assertEquals(1, result.getAge());
        assertEquals("Mario Rossi", result.getOwnerName());

        verify(petRepository).save(petDomain);
        verify(petMapper).toDto(petDomain);
    }

    @Test
    void test_getPetById_successfully() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(petDomain));
        when(petMapper.toDto(petDomain)).thenReturn(petResponseDto);

        PetResponseDto result = petService.getById(1L);

        assertNotNull(result);
        assertEquals("Fido", result.getName());
        assertEquals("DOG", result.getSpecies());
        assertEquals(1, result.getAge());
        assertEquals("Mario Rossi", result.getOwnerName());
    }

    @Test
    void test_getPetById_notFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundExceptionPet.class, () -> petService.getById(1L));
    }

    @Test
    void test_updatePet_successfully() {
        PetRequestDto petRequestDtoUpdated = petRequestDto;
        petRequestDtoUpdated.setName("Max");

        PetResponseDto petResponseDtoUpdated = petResponseDto;
        petResponseDtoUpdated.setName("Max");

        Pet petUpdated = petDomain;
        petUpdated.setName("Max");

        when(petRepository.findById(1L)).thenReturn(Optional.of(petDomain));
        when(petMapper.partialUpdate(petDomain, petRequestDtoUpdated)).thenReturn(petUpdated);
        when(petRepository.save(petUpdated)).thenReturn(petUpdated);
        when(petMapper.toDto(petUpdated)).thenReturn(petResponseDtoUpdated);

        PetResponseDto result = petService.update(1L, petRequestDtoUpdated);

        assertEquals("Max", result.getName());

        assertEquals("DOG", result.getSpecies());
        assertEquals(1, result.getAge());
        assertEquals("Mario Rossi", result.getOwnerName());
    }

    @Test
    void test_updatePet_notFound() {
        assertThrows(PetNotFoundExceptionPet.class, () -> petService.update(1L, petRequestDto));
    }

    @Test
    void test_deletePet_successfully() {
        doNothing().when(petRepository).deleteById(1L);
        petService.delete(1L);
        verify(petRepository).deleteById(1L);
    }

    @Test
    void test_getAllPets_successfully() {
        Pet pet1 = petDomain;

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Max");
        pet2.setAge(2);
        pet2.setSpecies(PetSpecies.CAT);
        pet2.setOwnerName("Bianca Verdi");

        List<Pet> pets = Arrays.asList(pet1, pet2);

        PetResponseDto dto1 = petResponseDto;
        PetResponseDto dto2 = new PetResponseDto();
        dto2.setId(pet2.getId());
        dto2.setName(pet2.getName());
        dto2.setAge(pet2.getAge());
        dto2.setSpecies(pet2.getSpecies().name());
        dto2.setOwnerName(pet2.getOwnerName());

        when(petRepository.findAll()).thenReturn(pets);

        when(petMapper.toDto(pet1)).thenReturn(dto1);
        when(petMapper.toDto(pet2)).thenReturn(dto2);

        List<PetResponseDto> result = petService.getAll();

        assertEquals(2, result.size());
        assertEquals("Fido", result.get(0).getName());
        assertEquals("Max", result.get(1).getName());
    }
}

