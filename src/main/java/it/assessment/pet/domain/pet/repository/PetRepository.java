package it.assessment.pet.domain.pet.repository;

import it.assessment.pet.domain.pet.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(Long id);
    void deleteById(Long id);
    List<Pet> findAll();
}
