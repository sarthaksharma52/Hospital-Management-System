package cg.hospital.projection;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import cg.hospital.entity.Appointment;

@Projection(name = "appointmentView", types = {Appointment.class})
public interface AppointmentProjection {

    Integer getAppointmentId();

    LocalDateTime getStarto();

    LocalDateTime getEndo();

    String getExaminationRoom();

    // Flatten Patient → name only
    @Value("#{target.patient.name}")
    String getPatientName();

    // Flatten Patient SSN (useful as identifier on the frontend)
    @Value("#{target.patient.ssn}")
    Integer getPatientSsn();

    // Flatten Physician → name only
    @Value("#{target.physician.name}")
    String getPhysicianName();

    @Value("#{target.physician.employeeId}")
    Integer getPhysicianId();

    // PrepNurse is nullable – guard against NullPointerException with ternary SpEL
    @Value("#{target.prepNurse != null ? target.prepNurse.name : null}")
    String getPrepNurseName();

    @Value("#{target.prepNurse != null ? target.prepNurse.employeeId : null}")
    Integer getPrepNurseId();
}
