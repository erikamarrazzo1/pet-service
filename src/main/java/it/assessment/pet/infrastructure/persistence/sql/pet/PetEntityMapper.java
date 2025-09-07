package it.assessment.pet.infrastructure.persistence.sql.pet;

import it.assessment.pet.domain.pet.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetEntityMapper {

    public PetEntity toEntity(Pet pet) {
        PetEntity petEntity = new PetEntity();
        petEntity.setId(pet.getId());
        petEntity.setName(pet.getName());
        petEntity.setSpecies(pet.getSpecies());
        petEntity.setAge(pet.getAge());
        petEntity.setOwnerName(pet.getOwnerName());
        return petEntity;
    }

    public Pet toDomain(PetEntity petEntity) {
        Pet pet = new Pet();
        pet.setId(petEntity.getId());
        pet.setName(petEntity.getName());
        pet.setSpecies(petEntity.getSpecies());
        pet.setAge(petEntity.getAge());
        pet.setOwnerName(petEntity.getOwnerName());
        return pet;
    }
}
