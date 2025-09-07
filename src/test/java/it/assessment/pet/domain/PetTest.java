package it.assessment.pet.domain;

import static org.junit.jupiter.api.Assertions.*;

import it.assessment.pet.domain.pet.exception.InvalidPetAgeExceptionPet;
import it.assessment.pet.domain.pet.exception.PetDomainException;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    void test_setAge_negativeValue() {
        Pet pet = new Pet();
        InvalidPetAgeExceptionPet exception =
                assertThrows(InvalidPetAgeExceptionPet.class, () -> pet.setAge(-1));

        assertEquals("Invalid input age: -1. Age must be zero or positive.", exception.getMessage());
    }

    @Test
    void test_setAge_zeroValue() {
        Pet pet = new Pet();
        pet.setAge(0);

        assertEquals(0, pet.getAge());
    }

    @Test
    void test_setAge_positiveValue() {
        Pet pet = new Pet();
        pet.setAge(5);

        assertEquals(5, pet.getAge());
    }

    @Test
    void test_setAge_nullValue() {
        Pet pet = new Pet();
        pet.setAge(null);

        assertNull(pet.getAge());
    }

    @Test
    void test_setValidMandatoryFields() {
        Pet pet = new Pet();

        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies(PetSpecies.DOG);

        assertEquals(1L, pet.getId());
        assertEquals("Fido", pet.getName());
        assertEquals(PetSpecies.DOG, pet.getSpecies());
        assertNull(pet.getOwnerName());
        assertNull(pet.getAge());
    }

    @Test
    void test_setNullMandatoryFields() {
        Pet pet = new Pet();
        assertThrows(PetDomainException.class, () -> pet.setName(null));
        assertThrows(PetDomainException.class, () -> pet.setSpecies(null));
    }
}
