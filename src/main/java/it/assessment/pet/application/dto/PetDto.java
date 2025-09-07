package it.assessment.pet.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class PetDto {
    private Long id;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;
}
