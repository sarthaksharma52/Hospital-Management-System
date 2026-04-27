package cg.hospital.repository;

import cg.hospital.entity.Procedures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProceduresRepositoryTest {

	@Autowired
	private ProceduresRepository proceduresRepository;

	@Test
	void testFindAllProcedures() {

		// Act
		List<Procedures> list = proceduresRepository.findAll();

		// Assert
		assertNotNull(list);
		assertTrue(list.size() > 0);

		// Debug print
		list.forEach(System.out::println);
	}
}