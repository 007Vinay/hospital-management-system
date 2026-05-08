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

    //DTO Response METHOD
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
    public Appointment createAppointment(Long patientId,
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

        return appointmentRepository.save(appointment);
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }


    //Update Appointment
    public Appointment updateAppointment(Long id,
                                         AppointmentRequestDTO requestDTO){
        Appointment existingAppointment = getAppointmentEntityById(id);

        existingAppointment.setAppointmentDate(
                requestDTO.getAppointmentDate());

        existingAppointment.setStatus(
                requestDTO.getStatus());

        return appointmentRepository.save(existingAppointment);
    }

    //Delete Appointment
    public void deleteAppointment(Long id){
        Appointment appointment = getAppointmentEntityById(id);

        appointmentRepository.delete(appointment);
    }
}
