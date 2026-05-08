package com.hospital.hms.service;

import com.hospital.hms.dto.AppointmentRequestDTO;
import com.hospital.hms.dto.AppointmentResponseDTO;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.entity.Patient;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.AppointmentRepository;
import com.hospital.hms.repository.DoctorRepository;
import com.hospital.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    //Internal Entity METHOD
    private Appointment getAppointmentEntityById(Long id){
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: "+ id));
    }

    //DTO Response METHOD for GET by ID
    public AppointmentResponseDTO getAppointmentById(Long id){
        Appointment appointment = getAppointmentEntityById(id);

        return mapToDTO(appointment);
    }

    //Helper Method for Convert Entity -> DTO (GET)
    private AppointmentResponseDTO mapToDTO(Appointment appointment){
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName()
        );
    }


    //Create Appointment
    public AppointmentResponseDTO createAppointment(Long patientId,
                                         Long doctorId,
                                         AppointmentRequestDTO requestDTO){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: "+ patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: "+ doctorId));

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(
                requestDTO.getAppointmentDate());

        appointment.setStatus(
                requestDTO.getStatus());

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToDTO(savedAppointment);
    }

    // Get All Appointments
    public Page<AppointmentResponseDTO> getAllAppointments(
            int page,
            int size,
            String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Appointment> appointmentPage =
                appointmentRepository.findAll(pageable);

        return appointmentPage.map(this::mapToDTO);
    }


    //Update Appointment
    public AppointmentResponseDTO updateAppointment(Long id,
                                         AppointmentRequestDTO requestDTO){
        Appointment existingAppointment = getAppointmentEntityById(id);

        existingAppointment.setAppointmentDate(
                requestDTO.getAppointmentDate());

        existingAppointment.setStatus(
                requestDTO.getStatus());

        Appointment updatedAppointment =
                appointmentRepository.save(existingAppointment);

        return mapToDTO(updatedAppointment);
    }

    //Delete Appointment
    public void deleteAppointment(Long id){
        Appointment appointment = getAppointmentEntityById(id);

        appointmentRepository.delete(appointment);
    }

    //Retrieves appointments by status
    public List<AppointmentResponseDTO>getAppointmentByStatus(String status){

        List<Appointment> appointments =
                appointmentRepository.findByStatus(status);

        return appointments.stream()
                .map(this::mapToDTO)
                .toList();
    }
}
