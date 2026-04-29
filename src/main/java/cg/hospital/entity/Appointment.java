package cg.hospital.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentID", nullable = false)
    private Integer appointmentID;

    // Mapped as an Integer because the Patient entity belongs to another team
    // member
    @Column(name = "Patient", nullable = false)
    private Integer patient;

    @Column(name = "PrepNurse") // Nullable based on schema
    private Integer prepNurse;

    @Column(name = "Physician", nullable = false)
    private Integer physician;

    @Column(name = "Starto", nullable = false)
    private LocalDateTime starto;

    @Column(name = "Endo", nullable = false)
    private LocalDateTime endo;

    @Column(name = "ExaminationRoom", nullable = false, columnDefinition = "TEXT")
    private String examinationRoom;

    // The HATEOAS relationship builder
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Prescribes prescriptions;

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
    }

    public Integer getPrepNurse() {
        return prepNurse;
    }

    public void setPrepNurse(Integer prepNurse) {
        this.prepNurse = prepNurse;
    }

    public Integer getPhysician() {
        return physician;
    }

    public void setPhysician(Integer physician) {
        this.physician = physician;
    }

    public LocalDateTime getStarto() {
        return starto;
    }

    public void setStarto(LocalDateTime starto) {
        this.starto = starto;
    }

    public LocalDateTime getEndo() {
        return endo;
    }

    public void setEndo(LocalDateTime endo) {
        this.endo = endo;
    }

    public String getExaminationRoom() {
        return examinationRoom;
    }

    public void setExaminationRoom(String examinationRoom) {
        this.examinationRoom = examinationRoom;
    }

    public Prescribes getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Prescribes prescriptions) {
        this.prescriptions = prescriptions;
    }

}