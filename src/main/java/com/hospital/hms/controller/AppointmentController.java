package com.hospital.hms.controller;

import com.hospital.hms.dto.AppointmentRequestDTO;
import com.hospital.hms.dto.AppointmentResponseDTO;
import com.hospital.hms.entity.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.hospital.hms.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    //Get (Fetch) by status
    @GetMapping("/status/{status}")
    public List<AppointmentResponseDTO> getAppointmentsByStatus(@PathVariable
                                                                String status){

        return appointmentService.getAppointmentByStatus(status);
    }

    //Get(Fetch) by Doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(@PathVariable
                                                                @Positive Long doctorId){

        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    //Get(Fetch) by Patient ID
    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDTO> getAppointmentByPatient(@PathVariable
                                                                @Positive Long patientId){

        return appointmentService.getAppointmentsByPatient(patientId);
    }

    // Get appointments within a date range
    @GetMapping("/date-range")
    public List<AppointmentResponseDTO> getAppointmentsBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate){

        return appointmentService.getAppointmentsBetweenDates(startDate, endDate);
    }

    //Search Appointments with Filters
    @GetMapping("/search")
    public Page<AppointmentResponseDTO> searchAppointments(
            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            Long doctorId,

            @RequestParam(required = false)
            Long patientId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "appointmentDate")
            String sortBy){

        return appointmentService.searchAppointments(
                status, doctorId, patientId, page, size, sortBy);
    }

    //Get appointments of currently logged-in patient
    @GetMapping("/my-appointments")
    public ResponseEntity<List<Appointment>> getMyAppointments(
            Authentication authentication) {

        //Extract username from authenticated JWT user
        String username = authentication.getName();

        //Fetch appointments from service layer
        List<Appointment> appointments = appointmentService
                        .getMyAppointments(username);

        //Return appointments of authenticated patient
        return ResponseEntity.ok(appointments);
    }
}
