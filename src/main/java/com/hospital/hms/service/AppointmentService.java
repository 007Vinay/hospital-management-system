package com.hospital.hms.service;

import com.hospital.hms.dto.AppointmentRequestDTO;
import com.hospital.hms.dto.AppointmentResponseDTO;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.AppointmentStatus;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    //Logger for AppointmentService
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

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

    //Helper Method for Convert Entity -> DTO (GET)
    private AppointmentResponseDTO mapToDTO(Appointment appointment){
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getPatient().getName(),
                appointment.getPatient().getAge(),
                appointment.getPatient().getGender(),
                appointment.getPatient().getDisease(),
                appointment.getPatient().getPhone(),
                appointment.getDoctor().getName()
        );
    }

    //Create new appointment for the specified patient and doctor
    public AppointmentResponseDTO createAppointment(
            String username, Long doctorId,
            AppointmentRequestDTO requestDTO){

        //Fetch authenticated patient using JWT username
        Patient patient = patientRepository.findByUserUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with username: "
                                                + username));

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

        logger.info("Appointment created with ID: {}", savedAppointment.getId());

        return mapToDTO(savedAppointment);
    }

    // Retrieves all appointments and converts them into a list of AppointmentResponseDTO objects.
    public Page<AppointmentResponseDTO> getAllAppointments(
            int page,
            int size,
            String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Appointment> appointmentPage =
                appointmentRepository.findAll(pageable);

        logger.info("Fetching all appointments");

        return appointmentPage.map(this::mapToDTO);
    }

    // Retrieve appointment by its ID and returns the mapped response DTO
    public AppointmentResponseDTO getAppointmentById(Long id){
        Appointment appointment = getAppointmentEntityById(id);

        logger.info("Fetching appointment with ID: {}", id);

        return mapToDTO(appointment);
    }


    //Update existing appointment using the provided appointment ID
    public AppointmentResponseDTO updateAppointment(Long id,
                                         AppointmentRequestDTO requestDTO){
        Appointment existingAppointment = getAppointmentEntityById(id);

        existingAppointment.setAppointmentDate(
                requestDTO.getAppointmentDate());

        existingAppointment.setStatus(
                requestDTO.getStatus());

        Appointment updatedAppointment =
                appointmentRepository.save(existingAppointment);

        logger.info("Appointment updated with ID: {}",
                updatedAppointment.getId());

        return mapToDTO(updatedAppointment);
    }

    //Delete appointment by its ID
    public void deleteAppointment(Long id){
        Appointment appointment = getAppointmentEntityById(id);

        logger.info("Deleting appointment with ID: {}", id);

        appointmentRepository.delete(appointment);
    }

    //Retrieves appointments by status
    public List<AppointmentResponseDTO> getAppointmentByStatus(AppointmentStatus status){

        List<Appointment> appointments =
                appointmentRepository.findByStatus(status);

        logger.info("Filtering appointments by status: {}", status);

        return appointments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Retrieve appointments for a specific doctor(Filter by Doctor)
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId){

        List<Appointment> appointments =
                appointmentRepository.findByDoctorId(doctorId);

        logger.info("Filtering appointments for doctor ID: {}", doctorId);
        return appointments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Retrieve appointments for a specific patient(Filter by Patient)
    public List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId){

        List<Appointment> appointments =
                appointmentRepository.findByPatientId(patientId);

        logger.info("Filtering appointments for patient ID: {}", patientId);

        return appointments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Retrieve appointments within the specified date range
    public List<AppointmentResponseDTO> getAppointmentsBetweenDates(
            LocalDateTime startDate, LocalDateTime endDate){

        List<Appointment> appointments = appointmentRepository.findAppointmentsBetweenDates(
                startDate, endDate);

        logger.info("Filtering appointments between " +
                "{} and {}", startDate, endDate);

        return appointments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Handles filtered appointment search with pageable response and DTO mapping
    public Page<AppointmentResponseDTO> searchAppointments(AppointmentStatus status, Long doctorId,
                                                           Long patientId, int page, int size, String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Appointment> appointments = appointmentRepository.searchAppointments(
                status, doctorId, patientId, pageable);

        logger.info("Filtering paginated appointments | " +
                "page: {}, size: {}, sortBy: {}", page, size, sortBy);

        return appointments.map(this::mapToDTO);
    }

    //Fetch appointments of currently authenticated patient
    public List<Appointment> getMyAppointments(String username) {

        logger.info("Fetching appointments for patient username: {}", username);

        return appointmentRepository.findByPatientUserUsername(username);
    }

    //Create appointment by admin/doctor using patient ID
    public AppointmentResponseDTO createAppointment(Long patientId,
            Long doctorId, AppointmentRequestDTO requestDTO){

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + doctorId));

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(requestDTO.getAppointmentDate());

        appointment.setStatus(requestDTO.getStatus());

        appointment.setPatient(patient);

        appointment.setDoctor(doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        logger.info("Appointment created by admin/doctor with ID: {}",
                savedAppointment.getId());

        return mapToDTO(savedAppointment);
    }

    //Update appointment status
    public AppointmentResponseDTO updateAppointmentStatus(Long id, AppointmentStatus status){

        Appointment appointment = getAppointmentEntityById(id);

        appointment.setStatus(status);

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        logger.info("Appointment status updated to {} for appointment ID: {}",
                status, id);

        return mapToDTO(updatedAppointment);
    }
}
