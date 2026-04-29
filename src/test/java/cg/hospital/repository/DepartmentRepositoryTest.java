package cg.hospital.repository;

import cg.hospital.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    // ✅ GET ALL
    @Test
    void shouldReturnAllDepartments() {
        List<Department> result = departmentRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isGreaterThanOrEqualTo(3); // based on your DB
    }

    // ✅ FIND BY ID (EXISTS)
    @Test
    void shouldFindById_whenExists() {
        Optional<Department> result = departmentRepository.findById(1);

        assertThat(result).isPresent();
    }

    @Test
    void shouldReturnEmpty_whenIdNotExists() {
        List<Department> all = departmentRepository.findAll();

        int nonExistingId = all.stream()
                .mapToInt(Department::getDepartmentID)
                .max()
                .orElse(0) + 1000;

        Optional<Department> result = departmentRepository.findById(nonExistingId);

        assertThat(result).isEmpty();
    }

    // ✅ FIND BY NAME (EXISTS)
    @Test
    void shouldFindByName_whenExists() {
        Optional<Department> result = departmentRepository.findByName("Surgery");

        assertThat(result).isPresent();
    }

    // ✅ FIND BY NAME (NOT EXISTS)
    @Test
    void shouldReturnEmpty_whenNameNotExists() {
        Optional<Department> result = departmentRepository.findByName("XYZ");

        assertThat(result).isEmpty();
    }

    // ✅ FIND BY HEAD (EXISTS)
    @Test
    void shouldFindByHeadEmployeeId_whenExists() {
        List<Department> result = departmentRepository.findByHead_EmployeeId(7);

        assertThat(result).isNotEmpty();
    }

    // ✅ FIND BY HEAD (NOT EXISTS)
    @Test
    void shouldReturnEmpty_whenHeadEmployeeIdNotExists() {
        List<Department> result = departmentRepository.findByHead_EmployeeId(999);

        assertThat(result).isEmpty();
    }

    // ✅ EXISTS TRUE
    @Test
    void shouldReturnTrue_whenExistsByHead() {
        boolean result = departmentRepository.existsByHead_EmployeeId(4);

        assertThat(result).isTrue();
    }

    // ✅ EXISTS FALSE
    @Test
    void shouldReturnFalse_whenNotExistsByHead() {
        boolean result = departmentRepository.existsByHead_EmployeeId(1);

        assertThat(result).isFalse();
    }

    // ✅ COUNT
    @Test
    void shouldReturnCount() {
        long count = departmentRepository.count();

        assertThat(count).isGreaterThan(0);
    }

    // ✅ OPTIONAL: VALIDATE DATA (STRONG TEST)
    @Test
    void shouldReturnDepartment_whenIdExists() {
        Optional<Department> result = departmentRepository.findById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isNotBlank();
    }
}