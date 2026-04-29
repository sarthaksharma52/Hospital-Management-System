package cg.hospital.repository;

import cg.hospital.entity.Procedures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// @DataJpaTest  → spins up ONLY the JPA layer (no web server, no controllers)
//                 Much faster than @SpringBootTest
// Replace.NONE  → use YOUR real DB, not an in-memory H2 DB
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProceduresRepositoryTest {

    @Autowired
    private ProceduresRepository proceduresRepository;

    // ✅ TEST 1 — Save a procedure and fetch it back by ID
    @Test
    void testSaveAndFindById() {
        Procedures p = new Procedures(9001, "Test Rhinoplasty", 1500.0);
        proceduresRepository.save(p);

        Optional<Procedures> result = proceduresRepository.findById(9001);

        assertTrue(result.isPresent(), "Procedure should be found after saving");
        assertEquals("Test Rhinoplasty", result.get().getName());
        assertEquals(1500.0, result.get().getCost());

        proceduresRepository.deleteById(9001); // cleanup
    }

    // ✅ TEST 2 — Find by ID that does NOT exist → should return empty, not crash
    @Test
    void testFindById_notFound() {
        Optional<Procedures> result = proceduresRepository.findById(99999);
        assertFalse(result.isPresent(), "Should return empty Optional for missing code");
    }

    // ✅ TEST 3 — findAll() should return at least the seed data rows
    @Test
    void testFindAll_notEmpty() {
        List<Procedures> list = proceduresRepository.findAll();
        assertNotNull(list, "List should not be null");
        assertFalse(list.isEmpty(), "There should be at least one procedure in DB");
        System.out.println(">> Total procedures in DB: " + list.size());
    }

    // ✅ TEST 4 — Save, then update cost, then verify the new cost is stored
    @Test
    void testUpdate_procedureCost() {
        Procedures p = new Procedures(9002, "Cost Update Test", 1000.0);
        proceduresRepository.save(p);

        p.setCost(2000.0);
        proceduresRepository.save(p); // save again = UPDATE (same PK)

        Optional<Procedures> updated = proceduresRepository.findById(9002);
        assertTrue(updated.isPresent());
        assertEquals(2000.0, updated.get().getCost(), "Cost should now be 2000");

        proceduresRepository.deleteById(9002);
    }

    // ✅ TEST 5 — existsById() should return true/false correctly
    @Test
    void testExistsById() {
        Procedures p = new Procedures(9003, "Exists Test", 500.0);
        proceduresRepository.save(p);

        assertTrue(proceduresRepository.existsById(9003),   "Should exist after save");
        assertFalse(proceduresRepository.existsById(99999), "Should not exist for unknown code");

        proceduresRepository.deleteById(9003);
    }
}