package cg.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Procedures;

@RepositoryRestResource(path = "procedures", collectionResourceRel = "procedures")
public interface ProceduresRepository extends JpaRepository<Procedures, Integer> {
//	findAll procedures List -> http://localhost:9876/procedures

//	find Physicians following that procedures

}