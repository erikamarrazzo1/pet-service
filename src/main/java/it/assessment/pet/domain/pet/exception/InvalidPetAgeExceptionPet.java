package it.assessment.pet.domain.pet.exception;

public class InvalidPetAgeExceptionPet extends PetDomainException {
    public InvalidPetAgeExceptionPet(Integer age) {
        super("Invalid input age: " + age + ". Age must be zero or positive.");
    }
}
