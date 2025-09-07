package it.assessment.pet.infrastructure.sql;

import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import it.assessment.pet.infrastructure.persistence.sql.pet.JpaPetBaseRepository;
import it.assessment.pet.infrastructure.persistence.sql.pet.JpaPetRepository;
import it.assessment.pet.infrastructure.persistence.sql.pet.PetEntity;
import it.assessment.pet.infrastructure.persistence.sql.pet.PetEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaPetRepositoryTest {

    private JpaPetBaseRepository petBaseRepo;
    private PetEntityMapper mapper;
    private JpaPetRepository petRepo;

    @BeforeEach
    void setUp() {
        petBaseRepo = mock(JpaPetBaseRepository.class);
        mapper = mock(PetEntityMapper.class);
        petRepo = new JpaPetRepository(petBaseRepo, mapper);
    }

    @Test
    void test_savePet_successfully() {
        Pet domainPet = new Pet();
        domainPet.setId(null);
        domainPet.setName("Fido");
        domainPet.setSpecies(PetSpecies.DOG);
        domainPet.setAge(1);

        PetEntity entityPet = new PetEntity();
        PetEntity savedEntity = new PetEntity();
        savedEntity.setId(1L);

        when(mapper.toEntity(domainPet)).thenReturn(entityPet);
        when(petBaseRepo.save(entityPet)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(domainPet);

        Pet result = petRepo.save(domainPet);

        assertNotNull(result);
        verify(mapper).toEntity(domainPet);
        verify(petBaseRepo).save(entityPet);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void test_findPetById_successfully() {
        PetEntity entity = new PetEntity();
        entity.setId(1L);

        Pet domain = new Pet();
        domain.setId(1L);

        when(petBaseRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<Pet> result = petRepo.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(petBaseRepo).findById(1L);
        verify(mapper).toDomain(entity);
    }

    @Test
    void test_findPetById_notFound() {
        when(petBaseRepo.findById(1L)).thenReturn(Optional.empty());

        Optional<Pet> result = petRepo.findById(1L);

        assertFalse(result.isPresent());
        verify(petBaseRepo).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void test_deletePetById_successfully() {
        doNothing().when(petBaseRepo).deleteById(1L);
        petRepo.deleteById(1L);
        verify(petBaseRepo).deleteById(1L);
    }

    @Test
    void test_findAllPets_successfully() {
        PetEntity entity1 = new PetEntity();
        PetEntity entity2 = new PetEntity();
        Pet pet1 = new Pet();
        Pet pet2 = new Pet();

        when(petBaseRepo.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(pet1);
        when(mapper.toDomain(entity2)).thenReturn(pet2);

        List<Pet> result = petRepo.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(pet1));
        assertTrue(result.contains(pet2));
        verify(petBaseRepo).findAll();
        verify(mapper).toDomain(entity1);
        verify(mapper).toDomain(entity2);
    }
}

