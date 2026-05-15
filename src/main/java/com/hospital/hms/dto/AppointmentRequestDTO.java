package com.hospital.hms.dto;

import com.hospital.hms.entity.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in future")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Status is required")
    private AppointmentStatus status;

    //Constructors
    public AppointmentRequestDTO() {
    }

    public AppointmentRequestDTO(LocalDateTime appointmentDate, AppointmentStatus status) {
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }




}