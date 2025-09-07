package it.assessment.pet.domain.pet.mapper;

import it.assessment.pet.application.dto.PetDto;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetDto toDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setId(pet.getId());
        petDto.setName(pet.getName());
        petDto.setSpecies(pet.getSpecies().name());
        petDto.setAge(pet.getAge());
        petDto.setOwnerName(pet.getOwnerName());
        return petDto;
    }

    public Pet toDomain(PetDto petDto) {
        Pet pet = new Pet();
        pet.setId(petDto.getId());
        pet.setName(petDto.getName());
        pet.setAge(petDto.getAge());
        pet.setSpecies(PetSpecies.valueOf(petDto.getSpecies().toUpperCase()));
        pet.setOwnerName(petDto.getOwnerName());
        return pet;
    }

    public Pet partialUpdate(Pet pet, PetDto petDto) {
        pet.setName(petDto.getName() != null ? petDto.getName() : pet.getName());
        pet.setAge(petDto.getAge() != null ? petDto.getAge() : pet.getAge());
        pet.setSpecies(petDto.getSpecies() != null ? PetSpecies.valueOf(petDto.getSpecies().toUpperCase()) : pet.getSpecies());
        pet.setOwnerName(petDto.getOwnerName() != null ? petDto.getOwnerName() : pet.getOwnerName());
        return pet;
    }
}
