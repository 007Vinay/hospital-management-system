package com.hospital.hms.controller;

import com.hospital.hms.entity.Appointment;
import com.hospital.hms.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Appointment createAppointment(
            @RequestParam @Positive Long patientId,
            @RequestParam @Positive Long doctorId,
            @Valid @RequestBody Appointment appointment){

        return appointmentService.createAppointment(
                patientId, doctorId, appointment);
    }

    //Get All Appointments
    @GetMapping
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    //Get Appointment By ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id);
    }

    //Update Appointment
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable @Positive Long id,
                                         @Valid @RequestBody Appointment appointment){
        return appointmentService.updateAppointment(id, appointment);
    }

    //Delete Appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);

        return "Appointment Deleted Successfully";
    }
}
