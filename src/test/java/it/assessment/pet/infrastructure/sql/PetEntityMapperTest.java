package it.assessment.pet.infrastructure.sql;

import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import it.assessment.pet.infrastructure.persistence.sql.pet.PetEntity;
import it.assessment.pet.infrastructure.persistence.sql.pet.PetEntityMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PetEntityMapperTest {

    private final PetEntityMapper mapper = new PetEntityMapper();

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void test_fromDomainToEntity(PetSpecies petSpecies) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies(petSpecies);
        pet.setAge(1);
        pet.setOwnerName("Mario Rossi");

        PetEntity entity = mapper.toEntity(pet);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Fido", entity.getName());
        assertEquals(petSpecies, entity.getSpecies());
        assertEquals(1, entity.getAge());
        assertEquals("Mario Rossi", entity.getOwnerName());
    }

    @ParameterizedTest
    @EnumSource(PetSpecies.class)
    void testToDomain(PetSpecies petSpecies) {
        PetEntity entity = new PetEntity();
        entity.setId(1L);
        entity.setName("Fido");
        entity.setSpecies(petSpecies);
        entity.setAge(1);
        entity.setOwnerName("Mario Rossi");

        Pet pet = mapper.toDomain(entity);

        assertNotNull(pet);
        assertEquals(1L, pet.getId());
        assertEquals("Fido", pet.getName());
        assertEquals(petSpecies, pet.getSpecies());
        assertEquals(1, pet.getAge());
        assertEquals("Mario Rossi", pet.getOwnerName());
    }
}
