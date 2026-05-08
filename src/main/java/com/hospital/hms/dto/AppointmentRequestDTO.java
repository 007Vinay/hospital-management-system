package com.hospital.hms.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in future")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Status is required")
    private String status;

    //Constructors
    public AppointmentRequestDTO() {
    }

    public AppointmentRequestDTO(LocalDateTime appointmentDate, String status) {
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    //Getters and Setters
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




}
