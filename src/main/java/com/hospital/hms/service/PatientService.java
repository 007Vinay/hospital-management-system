package com.hospital.hms.service;

import com.hospital.hms.dto.PatientRequestDTO;
import com.hospital.hms.dto.PatientResponseDTO;
import com.hospital.hms.entity.Patient;
import com.hospital.hms.repository.PatientRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.hms.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class PatientService {

    //Logger for PatientService
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    //Internal Entity Method
    private Patient getPatientEntityById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "+ id));
    }

    //Helper Method (Convert Entity -> ResponseDTO)
    private PatientResponseDTO mapToDTO(Patient patient){
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getPhone(),
                patient.getAge(),
                patient.getDisease(),
                patient.getGender()
        );
    }

    //Create new Patient and return PatientResponseDTO after saving
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO){
        Patient patient = new Patient();

        patient.setName(requestDTO.getName());
        patient.setPhone(requestDTO.getPhone());
        patient.setAge(requestDTO.getAge());
        patient.setDisease(requestDTO.getDisease());
        patient.setGender(requestDTO.getGender());

        Patient savedPatient = patientRepository.save(patient);

        logger.info("Patient created with ID: {}",
                savedPatient.getId());

        return mapToDTO(savedPatient);
    }

    //Retrieve all patients from database and map each entity to PatientResponseDTO
    public List<PatientResponseDTO> getAllPatients(){

        logger.info("Fetching all patients");

        return patientRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Retrieve Patient from database by ID, then map entity to PatientResponseDTO
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = getPatientEntityById(id);

        logger.info("Fetching patient with ID: {}", id);
        return mapToDTO(patient);
    }

    //Fetch existing Patient, update fields from request DTO, save changes, and return response DTO
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO){
        Patient existingPatient = getPatientEntityById(id);

        existingPatient.setName(requestDTO.getName());
        existingPatient.setPhone(requestDTO.getPhone());
        existingPatient.setAge(requestDTO.getAge());
        existingPatient.setDisease(requestDTO.getDisease());
        existingPatient.setGender(requestDTO.getGender());

        Patient updatedPatient = patientRepository.save(existingPatient);

        logger.info("Patient updated with ID: {}", updatedPatient.getId());

        return mapToDTO(updatedPatient);
    }

    //Retrieve Patient entity by ID and remove it from repository
    public void deletePatient(Long id){
        Patient patient = getPatientEntityById(id);

        logger.info("Deleting patient with ID: {}", id);

        patientRepository.delete(patient);
    }

}
