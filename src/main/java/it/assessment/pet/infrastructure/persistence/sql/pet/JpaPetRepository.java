package it.assessment.pet.infrastructure.persistence.sql.pet;

import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@ConditionalOnProperty(name = "application.datasource", havingValue = "h2", matchIfMissing = true)
public class JpaPetRepository implements PetRepository {

    private final JpaPetBaseRepository repo;
    private final PetEntityMapper mapper;

    public JpaPetRepository(JpaPetBaseRepository repo,
                            PetEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Pet save(Pet pet) {
        log.info("Saving Pet Entity into database...");
        PetEntity petEntity = mapper.toEntity(pet);
        petEntity = repo.save(petEntity);
        return mapper.toDomain(petEntity);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        log.info("Retrieving Pet Entity with id {} from the database...", id);
        Optional<PetEntity> petEntity = repo.findById(id);
        return petEntity.map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting Pet Entity with id {} from the database...", id);
        repo.deleteById(id);
    }

    @Override
    public List<Pet> findAll() {
        log.info("Retrieving all Pets Entity from the database...");
        List<PetEntity> petEntities = repo.findAll();
        return petEntities.stream().map(mapper::toDomain).toList();
    }
}
