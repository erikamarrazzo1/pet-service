package it.assessment.pet.domain.pet.model;

import it.assessment.pet.domain.pet.exception.PetDomainException;
import it.assessment.pet.domain.pet.exception.InvalidPetAgeExceptionPet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class Pet {

    private Long id;

    private String name;

    private PetSpecies species;

    private Integer age;

    private String ownerName;

    public void setAge(Integer age) {
        if (age != null && age < 0) {
            throw new InvalidPetAgeExceptionPet(age);
        }
        this.age = age;
    }

    public void setName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new PetDomainException("Pet name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setSpecies(PetSpecies species) {
        if (species == null) {
            throw new PetDomainException("PetSpecies cannot be null.");
        }
        this.species = species;
    }
}
