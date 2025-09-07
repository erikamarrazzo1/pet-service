package it.assessment.pet.infrastructure.persistence.sql.pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPetBaseRepository extends JpaRepository<PetEntity, Long> {
}
