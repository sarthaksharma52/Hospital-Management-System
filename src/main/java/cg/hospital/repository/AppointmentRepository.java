package cg.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Appointment;
import cg.hospital.projection.AppointmentProjection;
 
/**
 * Repository for Appointment.
 *
 * Constraints enforced:
 * - All list queries are Pageable
 * - Projection = AppointmentProjection (excerpt + default)
 */
@RepositoryRestResource(
        path = "appointments",
        collectionResourceRel = "appointments",
        excerptProjection = AppointmentProjection.class
)
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
 
    /* ── Page 1: list all appointments sorted by start time DESC ─────── */
 
    /**
     * Returns all appointments sorted descending by Starto.
     * Uses JPA query derivation — NOT findAll().
     * The "findBy" prefix with no predicate is valid derived syntax in
     * Spring Data JPA and translates to SELECT ... ORDER BY starto DESC.
     */
    Page<Appointment> findByOrderByStartoDesc(Pageable pageable);
 
    /* ── Additional search endpoints exposed via Spring Data REST ─────── */
 
    /**
     * GET /appointments/search/findByPatient_Ssn?patientSsn={ssn}
     * Fetch all appointments for a given patient.
     */
    Page<Appointment> findByPatient_Ssn(
            @Param("patientSsn") Integer patientSsn,
            Pageable pageable
    );
 
    /**
     * GET /appointments/search/findByPhysician_EmployeeId?physicianId={id}
     * Fetch all appointments handled by a given physician.
     */
    Page<Appointment> findByPhysician_EmployeeId(
            @Param("physicianId") Integer physicianId,
            Pageable pageable
    );
}