package cg.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cg.hospital.entity.Appointment;
import cg.hospital.entity.Prescribes;
import cg.hospital.entity.PrescribesId;
import cg.hospital.projection.PrescribesProjection;
 
/**
 * Repository for Prescribes (Prescription).
 *
 * Constraints enforced:
 * - READ ONLY: save and delete methods are hidden from REST exposure
 * - All queries use Pageable
 * - Projection = PrescribesProjection
 */
@RepositoryRestResource(
        path = "prescriptions",
        collectionResourceRel = "prescriptions",
        excerptProjection = PrescribesProjection.class
)
public interface PrescribesRepository extends JpaRepository<Prescribes, PrescribesId> {
 
    /* ── Page 2: prescriptions linked to a specific appointment ───────── */
 
    /**
     * GET /prescriptions/search/findByAppointment?appointmentId={id}
     *
     * Fetches all prescriptions for a given Appointment entity.
     * The frontend supplies the appointment ID; Spring Data REST resolves
     * the Appointment entity automatically via its repository.
     */
    Page<Prescribes> findByAppointment(
            @Param("appointment") Appointment appointment,
            Pageable pageable
    );
 
    /**
     * GET /prescriptions/search/findByAppointment_AppointmentId?id={id}
     *
     * Convenience search using the appointment's raw ID (integer),
     * so the frontend does not need to POST an entity URI.
     */
    Page<Prescribes> findByAppointment_AppointmentId(
            @Param("id") Integer appointmentId,
            Pageable pageable
    );
 
    /**
     * GET /prescriptions/search/findByPatient_Ssn?patientSsn={ssn}
     */
    Page<Prescribes> findByPatient_Ssn(
            @Param("patientSsn") Integer patientSsn,
            Pageable pageable
    );
}