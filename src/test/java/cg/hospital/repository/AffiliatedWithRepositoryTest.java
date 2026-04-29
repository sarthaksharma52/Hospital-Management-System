package cg.hospital.repository;

import cg.hospital.entity.AffiliatedWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AffiliatedWithRepositoryTest {

    @Autowired
    private AffiliatedWithRepository repository;

    // ✅ GET ALL
    @Test
    void shouldReturnAllAffiliations() {
        List<AffiliatedWith> result = repository.findAll();
        assertThat(result).isNotEmpty();
    }

    // ✅ FIND BY DEPARTMENT (EXISTS)
    @Test
    void shouldFindByDepartmentID_whenExists() {
        List<AffiliatedWith> all = repository.findAll();
        Integer deptId = all.get(0).getDepartment().getDepartmentID();

        List<AffiliatedWith> result =
                repository.findByDepartment_DepartmentID(deptId);

        assertThat(result).isNotEmpty();
    }

    // ✅ FIND BY DEPARTMENT (NOT EXISTS)
    @Test
    void shouldReturnEmpty_whenDepartmentIDNotExists() {
        List<AffiliatedWith> result =
                repository.findByDepartment_DepartmentID(Integer.MAX_VALUE);

        assertThat(result).isEmpty();
    }

    // ✅ FIND BY PHYSICIAN (EXISTS)
    @Test
    void shouldFindByPhysicianEmployeeId_whenExists() {
        List<AffiliatedWith> all = repository.findAll();
        Integer empId = all.get(0).getPhysician().getEmployeeId();

        List<AffiliatedWith> result =
                repository.findByPhysician_EmployeeId(empId);

        assertThat(result).isNotEmpty();
    }

    // ✅ FIND BY PHYSICIAN (NOT EXISTS)
    @Test
    void shouldReturnEmpty_whenPhysicianEmployeeIdNotExists() {
        List<AffiliatedWith> result =
                repository.findByPhysician_EmployeeId(Integer.MAX_VALUE);

        assertThat(result).isEmpty();
    }

    // ✅ COUNT (EXISTS)
    @Test
    void shouldReturnCount_whenDepartmentExists() {
        List<AffiliatedWith> all = repository.findAll();
        Integer deptId = all.get(0).getDepartment().getDepartmentID();

        long count =
                repository.countByDepartment_DepartmentID(deptId);

        assertThat(count).isGreaterThan(0);
    }

    // ✅ COUNT (NOT EXISTS)
    @Test
    void shouldReturnZeroCount_whenDepartmentNotExists() {
        long count =
                repository.countByDepartment_DepartmentID(Integer.MAX_VALUE);

        assertThat(count).isEqualTo(0);
    }

    // ✅ EXISTS TRUE
    @Test
    void shouldReturnTrue_whenPhysicianDepartmentLinkExists() {
        List<AffiliatedWith> all = repository.findAll();
        AffiliatedWith sample = all.get(0);

        boolean result =
                repository.existsByPhysician_EmployeeIdAndDepartment_DepartmentID(
                        sample.getPhysician().getEmployeeId(),
                        sample.getDepartment().getDepartmentID()
                );

        assertThat(result).isTrue();
    }

    // ✅ EXISTS FALSE
    @Test
    void shouldReturnFalse_whenPhysicianDepartmentLinkNotExists() {
        boolean result =
                repository.existsByPhysician_EmployeeIdAndDepartment_DepartmentID(
                        Integer.MAX_VALUE, Integer.MAX_VALUE
                );

        assertThat(result).isFalse();
    }

    // ⚠️ OPTIONAL (SAFE PRIMARY CHECK)
   @Test
    void shouldValidatePrimaryAffiliation_safely() {
        List<AffiliatedWith> result =
                repository.findByDepartment_DepartmentIDAndPrimaryAffiliationTrue(1);

        assertThat(result)
                .allMatch(AffiliatedWith::isPrimaryAffiliation);
    }
}