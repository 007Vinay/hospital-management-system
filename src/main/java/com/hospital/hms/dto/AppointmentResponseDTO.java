package com.hospital.hms.dto;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    private Long id;
    private LocalDateTime appointmentDate;
    private String status;
    private String patientName;
    private String doctorName;

    //Constructors
    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(Long id, LocalDateTime appointmentDate, String status, String patientName, String doctorName) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.patientName = patientName;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


}
