package it.assessment.pet.infrastructure.persistence.sql.pet;

import it.assessment.pet.domain.pet.model.Pet;
import it.assessment.pet.domain.pet.repository.PetRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        PetEntity petEntity = mapper.toEntity(pet);
        petEntity = repo.save(petEntity);
        return mapper.toDomain(petEntity);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        Optional<PetEntity> petEntity = repo.findById(id);
        return petEntity.map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Pet> findAll() {
        List<PetEntity> petEntities = repo.findAll();
        return petEntities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
