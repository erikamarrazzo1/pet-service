package it.assessment.pet.interfaces.rest.exception;

import it.assessment.pet.domain.pet.exception.InvalidPetAgeExceptionPet;
import it.assessment.pet.domain.pet.exception.PetDomainException;
import it.assessment.pet.domain.pet.exception.PetNotFoundExceptionPet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidPetAgeExceptionPet.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidPetAge(InvalidPetAgeExceptionPet ex,
                                        HttpServletRequest request) {
        return new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(PetDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidPetAge(PetDomainException ex,
                                        HttpServletRequest request) {
        return new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(PetNotFoundExceptionPet.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(PetNotFoundExceptionPet ex, HttpServletRequest request) {
        return new ApiError(
                Instant.now(),
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    public record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path
    ) {}
}
