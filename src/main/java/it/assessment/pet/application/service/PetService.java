package it.assessment.pet.application.service;

import it.assessment.pet.application.dto.PetRequestDto;
import it.assessment.pet.application.dto.PetResponseDto;
import it.assessment.pet.domain.pet.exception.PetNotFoundExceptionPet;
import it.assessment.pet.domain.pet.mapper.PetMapper;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public PetService(PetRepository petRepository,
                      PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public PetResponseDto create(PetRequestDto petRequestDto) {
        log.info("Creating new Pet: {}...", petRequestDto.toString());
        Pet pet = petMapper.createFromRequest(petRequestDto);
        pet = petRepository.save(pet);
        log.info("Pet created successfully.");
        return petMapper.toDto(pet);
    }

    public PetResponseDto update(Long id, PetRequestDto petUpdatedValues) {
        log.info("Updating Pet with id: {}. Values to update: {}...", id, petUpdatedValues.toString());
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundExceptionPet(id));
        pet = petMapper.partialUpdate(pet, petUpdatedValues);
        pet = petRepository.save(pet);
        log.info("Pet updated successfully.");
        return petMapper.toDto(pet);
    }

    public void delete(Long id) {
        log.info("Deleting Pet with id: {}...", id);
        petRepository.deleteById(id);
        log.info("Pet deleted successfully.");
    }

    public PetResponseDto getById(Long id) {
        log.info("Getting Pet by id: {}...", id);
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundExceptionPet(id));
        return petMapper.toDto(pet);
    }

    public List<PetResponseDto> getAll() {
        log.info("Getting all Pets...");
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(petMapper::toDto).toList();
    }
}
