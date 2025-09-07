package it.assessment.pet.application.service;

import it.assessment.pet.application.dto.PetDto;
import it.assessment.pet.domain.pet.exception.PetNotFoundExceptionPet;
import it.assessment.pet.domain.pet.mapper.PetMapper;
import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public PetDto create(PetDto petDto) {
        log.info("Creating new Pet: {}...", petDto.toString());
        Pet pet = petMapper.toDomain(petDto);
        pet = petRepository.save(pet);
        log.info("Pet created successfully.");
        return petMapper.toDto(pet);
    }

    public PetDto update(Long id, PetDto petUpdated) {
        log.info("Updating Pet with id: {}. Values to update: {}...", id, petUpdated.toString());
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundExceptionPet(id));
        pet = petMapper.partialUpdate(pet, petUpdated);
        pet = petRepository.save(pet);
        log.info("Pet updated successfully.");
        return petMapper.toDto(pet);
    }

    public void delete(Long id) {
        log.info("Deleting Pet with id: {}...", id);
        petRepository.deleteById(id);
        log.info("Pet deleted successfully.");
    }

    public PetDto getById(Long id) {
        log.info("Getting Pet by id: {}...", id);
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundExceptionPet(id));
        return petMapper.toDto(pet);
    }

    public List<PetDto> getAll() {
        log.info("Getting all Pets...");
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(petMapper::toDto).collect(Collectors.toList());
    }
}
