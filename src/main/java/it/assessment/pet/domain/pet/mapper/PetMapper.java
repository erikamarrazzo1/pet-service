package it.assessment.pet.domain.pet.mapper;

import it.assessment.pet.application.dto.PetRequestDto;
import it.assessment.pet.application.dto.PetResponseDto;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.model.PetSpecies;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetResponseDto toDto(Pet pet) {
        PetResponseDto petResponseDto = new PetResponseDto();
        petResponseDto.setId(pet.getId());
        petResponseDto.setName(pet.getName());
        petResponseDto.setSpecies(pet.getSpecies().name());
        petResponseDto.setAge(pet.getAge());
        petResponseDto.setOwnerName(pet.getOwnerName());
        return petResponseDto;
    }

    public Pet toDomain(PetResponseDto petResponseDto) {
        Pet pet = new Pet();
        pet.setId(petResponseDto.getId());
        pet.setName(petResponseDto.getName());
        pet.setAge(petResponseDto.getAge());
        pet.setSpecies(PetSpecies.valueOf(petResponseDto.getSpecies().toUpperCase()));
        pet.setOwnerName(petResponseDto.getOwnerName());
        return pet;
    }

    public Pet createFromRequest(PetRequestDto petRequestDto) {
        Pet pet = new Pet();
        pet.setName(petRequestDto.getName());
        pet.setAge(petRequestDto.getAge());
        pet.setSpecies(PetSpecies.valueOf(petRequestDto.getSpecies().toUpperCase()));
        pet.setOwnerName(petRequestDto.getOwnerName());
        return pet;
    }

    public Pet partialUpdate(Pet pet, PetRequestDto petRequestDto) {
        pet.setName(petRequestDto.getName() != null ? petRequestDto.getName() : pet.getName());
        pet.setAge(petRequestDto.getAge() != null ? petRequestDto.getAge() : pet.getAge());
        pet.setSpecies(petRequestDto.getSpecies() != null ? PetSpecies.valueOf(petRequestDto.getSpecies().toUpperCase()) : pet.getSpecies());
        pet.setOwnerName(petRequestDto.getOwnerName() != null ? petRequestDto.getOwnerName() : pet.getOwnerName());
        return pet;
    }
}
