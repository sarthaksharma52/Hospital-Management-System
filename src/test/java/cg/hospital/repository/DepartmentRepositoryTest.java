package cg.hospital.repository;

import cg.hospital.entity.Department;
import cg.hospital.entity.Physician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Physician physician1;
    private Physician physician2;
    private Department generalMedicine;
    private Department surgery;
    private Department psychiatry;

    @BeforeEach
    void setUp() {
        // Persist physicians first (FK dependency)
        physician1 = new Physician();
        physician1.setEmployeeId(4);
        physician1.setName("Percival Cox");
        physician1.setPosition("Senior Attending Physician");
        physician1.setSsn(444444444);
        entityManager.persist(physician1);

        physician2 = new Physician();
        physician2.setEmployeeId(7);
        physician2.setName("John Wen");
        physician2.setPosition("Surgical Attending Physician");
        physician2.setSsn(777777777);
        entityManager.persist(physician2);

        // Persist departments
        generalMedicine = new Department(1, "General Medicine", physician1);
        surgery = new Department(2, "Surgery", physician2);
        psychiatry = new Department(3, "Psychiatry", physician2);

        entityManager.persist(generalMedicine);
        entityManager.persist(surgery);
        entityManager.persist(psychiatry);

        entityManager.flush();
    }

    @Test
    @DisplayName("findAll should return all persisted departments")
    void testFindAll() {
        List<Department> all = departmentRepository.findAll();
        assertThat(all).hasSize(3);
    }

    @Test
    @DisplayName("findById should return department when ID exists")
    void testFindById_found() {
        Optional<Department> result = departmentRepository.findById(1);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("General Medicine");
    }

    @Test
    @DisplayName("findById should return empty when ID does not exist")
    void testFindById_notFound() {
        Optional<Department> result = departmentRepository.findById(999);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByName should return department when name matches")
    void testFindByName_found() {
        Optional<Department> result = departmentRepository.findByName("Surgery");
        assertThat(result).isPresent();
        assertThat(result.get().getDepartmentID()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByName should return empty when name does not match")
    void testFindByName_notFound() {
        Optional<Department> result = departmentRepository.findByName("Dermatology");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByHead_EmployeeId should return single department")
    void testFindByHeadEmployeeID_singleResult() {
        List<Department> result = departmentRepository.findByHead_EmployeeId(4);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("General Medicine");
    }

    @Test
    @DisplayName("findByHead_EmployeeId should return empty list")
    void testFindByHeadEmployeeID_noResults() {
        List<Department> result = departmentRepository.findByHead_EmployeeId(999);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("existsByHead_EmployeeId should return true")
    void testExistsByHeadEmployeeID_exists() {
        boolean exists = departmentRepository.existsByHead_EmployeeId(4);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existsByHead_EmployeeId should return false")
    void testExistsByHeadEmployeeID_notExists() {
        boolean exists = departmentRepository.existsByHead_EmployeeId(1);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("count should return correct number")
    void testCount() {
        long count = departmentRepository.count();
        assertThat(count).isEqualTo(3);
    }
}