package cg.hospital.projection;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Interface-based projection for Prescribes (prescription detail view).
 * Shows dose, date, and flattened names of linked entities.
 * Medication details are intentionally kept minimal (name only) per spec.
 * No nested objects are returned; all relations are flattened to primitives.
 */
@Projection(name = "prescribesView", types = { cg.hospital.entity.Prescribes.class })
public interface PrescribesProjection {

    // Core fields
    String getDose();

    LocalDateTime getDate();

    // Physician info
    @Value("#{target.physician.name}")
    String getPhysicianName();

    @Value("#{target.physician.employeeId}")
    Integer getPhysicianId();

    @Value("#{target.physician.position}")
    String getPhysicianPosition();

    // Patient info
    @Value("#{target.patient.name}")
    String getPatientName();

    @Value("#{target.patient.ssn}")
    Integer getPatientSsn();

    // Medication — name only, as per spec (not exposed directly)
    @Value("#{target.medication.name}")
    String getMedicationName();

    @Value("#{target.medication.brand}")
    String getMedicationBrand();

    // Appointment link — nullable
    @Value("#{target.appointment != null ? target.appointment.appointmentId : null}")
    Integer getAppointmentId();
}