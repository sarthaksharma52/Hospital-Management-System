package cg.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Prescribes;
import cg.hospital.entity.PrescribesId;

@RepositoryRestResource(collectionResourceRel = "prescriptions", path = "prescriptions")
public interface PrescribesRepository extends JpaRepository<Prescribes, PrescribesId> {
    
}