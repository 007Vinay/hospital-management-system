package com.hospital.hms.controller;

import com.hospital.hms.dto.AppointmentRequestDTO;
import com.hospital.hms.dto.AppointmentResponseDTO;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    //Create Appointment
    @PostMapping
    public AppointmentResponseDTO createAppointment(
            @RequestParam @Positive Long patientId,
            @RequestParam @Positive Long doctorId,
            @Valid @RequestBody AppointmentRequestDTO requestDTO){

        return appointmentService.createAppointment(
                patientId, doctorId, requestDTO);
    }

    //Get All Appointments
    @GetMapping
    public Page<AppointmentResponseDTO> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "appointmentDate")
            String sortBy){

        return appointmentService.getAllAppointments(
                page, size, sortBy);
    }

    //Get Appointment By ID
    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id);
    }

    //Update Appointment
    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable @Positive Long id,
                                         @Valid @RequestBody AppointmentRequestDTO requestDTO){
        return appointmentService.updateAppointment(id, requestDTO);
    }

    //Delete Appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);

        return "Appointment Deleted Successfully";
    }
}
