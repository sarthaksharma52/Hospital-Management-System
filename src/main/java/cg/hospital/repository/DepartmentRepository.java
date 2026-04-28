package cg.hospital.repository;

import cg.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "departments", path = "departments")
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByName(@Param("name") String name);

    List<Department> findByHead_EmployeeId(@Param("employeeId") Integer employeeId);

    boolean existsByHead_EmployeeId(@Param("employeeId") Integer employeeId);
}