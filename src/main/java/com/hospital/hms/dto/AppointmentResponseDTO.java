package com.hospital.hms.dto;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    private Long id;
    private LocalDateTime appointmentDate;
    private String status;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String patientDisease;
    private String patientPhone;
    private String doctorName;

    //Constructors
    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(
            Long id, LocalDateTime appointmentDate,
            String status, String patientName,
            Integer patientAge, String patientGender,
            String patientDisease, String patientPhone,
            String doctorName) {

        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientDisease = patientDisease;
        this.patientPhone = patientPhone;
        this.doctorName = doctorName;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientDisease() {
        return patientDisease;
    }

    public void setPatientDisease(String patientDisease) {
        this.patientDisease = patientDisease;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
