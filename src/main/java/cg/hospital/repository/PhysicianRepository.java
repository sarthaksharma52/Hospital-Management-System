package cg.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Physician;

@RepositoryRestResource(
    collectionResourceRel = "physicians",
    path = "physicians"
)
public interface PhysicianRepository extends JpaRepository<Physician, Integer> {

  
}
