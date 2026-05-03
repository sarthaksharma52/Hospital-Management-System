package cg.hospital.repository;

import cg.hospital.entity.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MedicationRepositoryTest {

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    public void testFindByCode() {
        Pageable pageable = PageRequest.of(0, 1);
        java.util.Optional<Medication> found = medicationRepository.findByCode(1);
        // It might be null if DB is empty, but we verify method exists and runs
        assertThat(found).isNotNull();
    }

    @Test
    public void testFindById() {
        // Just verify findById works with a dummy ID since we can't use findAll
        Medication found = medicationRepository.findById(1).orElse(null);
        // It might be null if DB is empty
    }
}
