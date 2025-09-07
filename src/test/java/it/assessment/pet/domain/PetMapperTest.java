package it.assessment.pet.domain;

import it.assessment.pet.application.dto.PetDto;
import it.assessment.pet.domain.pet.mapper.PetMapper;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PetMapperTest {

    private final PetMapper petMapper = new PetMapper();

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void test_fromDomainToDto(PetSpecies petSpecies) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies(petSpecies);
        pet.setAge(1);
        pet.setOwnerName("Mario Rossi");

        PetDto dto = petMapper.toDto(pet);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Fido", dto.getName());
        assertEquals(petSpecies.name(), dto.getSpecies());
        assertEquals(1, dto.getAge());
        assertEquals("Mario Rossi", dto.getOwnerName());
    }

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void test_fromDtoToDomain(PetSpecies petSpecies) {
        PetDto dto = new PetDto();
        dto.setId(1L);
        dto.setName("Fido");
        dto.setSpecies(petSpecies.name());
        dto.setAge(1);
        dto.setOwnerName("Mario Rossi");

        Pet pet = petMapper.toDomain(dto);

        assertNotNull(pet);
        assertEquals(1L, pet.getId());
        assertEquals("Fido", pet.getName());
        assertEquals(petSpecies, pet.getSpecies());
        assertEquals(1, pet.getAge());
        assertEquals("Mario Rossi", pet.getOwnerName());
    }

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void test_partialUpdate_allFieldsPresent(PetSpecies petSpecies) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies(petSpecies);
        pet.setAge(1);
        pet.setOwnerName("Mario Rossi");

        PetDto updateDto = new PetDto();
        updateDto.setName("Fido Updated");
        updateDto.setSpecies(petSpecies.name());
        updateDto.setAge(1);
        updateDto.setOwnerName("Mario Rossi Updated");

        Pet updatedPet = petMapper.partialUpdate(pet, updateDto);

        assertEquals("Fido Updated", updatedPet.getName());
        assertEquals(petSpecies, updatedPet.getSpecies());
        assertEquals(1, updatedPet.getAge());
        assertEquals("Mario Rossi Updated", updatedPet.getOwnerName());
    }

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void test_partialUpdate_someFieldsNull(PetSpecies petSpecies) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies(petSpecies);
        pet.setAge(1);
        pet.setOwnerName("Mario Rossi");

        PetDto updateDto = new PetDto();
        updateDto.setName(null);
        updateDto.setSpecies(null);
        updateDto.setAge(5);
        updateDto.setOwnerName(null);

        Pet updatedPet = petMapper.partialUpdate(pet, updateDto);

        assertEquals("Fido", updatedPet.getName());
        assertEquals(petSpecies, updatedPet.getSpecies());
        assertEquals(5, updatedPet.getAge());
        assertEquals("Mario Rossi", updatedPet.getOwnerName());
    }
}
