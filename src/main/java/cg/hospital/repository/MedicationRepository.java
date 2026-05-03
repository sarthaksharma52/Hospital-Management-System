package cg.hospital.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Medication;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource(collectionResourceRel = "medications", path = "medications", exported = false)
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    Optional<Medication> findByCode(@Param("code") Integer code);
}