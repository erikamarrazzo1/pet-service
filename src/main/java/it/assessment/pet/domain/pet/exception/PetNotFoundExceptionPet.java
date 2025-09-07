package it.assessment.pet.domain.pet.exception;

public class PetNotFoundExceptionPet extends PetDomainException {
    public PetNotFoundExceptionPet(Long id) {
        super("Pet with id: " + id + " not found.");
    }
}
