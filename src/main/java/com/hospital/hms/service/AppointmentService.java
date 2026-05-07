package com.hospital.hms.service;

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

    //Create Appointment
    public Appointment createAppointment(Long patientId,
                                         Long doctorId,
                                         Appointment appointment){

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: "+ patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: "+ doctorId));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    //Get Appointment By ID
    public Appointment getAppointmentById(Long id){
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: "+ id));
    }
    public Appointment updateAppointment(Long id,
                                         Appointment updateAppointment){
        Appointment existingAppointment = getAppointmentById(id);

        existingAppointment.setAppointmentDate(
                updateAppointment.getAppointmentDate());

        existingAppointment.setStatus(
                updateAppointment.getStatus());

        return appointmentRepository.save(existingAppointment);
    }

    //Delete Appointment
    public void deleteAppointment(Long id){
        Appointment appointment = getAppointmentById(id);

        appointmentRepository.delete(appointment);
    }
}
