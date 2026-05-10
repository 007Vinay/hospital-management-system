package com.hospital.hms.service;

import com.hospital.hms.dto.DoctorRequestDTO;
import com.hospital.hms.dto.DoctorResponseDTO;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class DoctorService {

    //Logger for DoctorService
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    //Internal Entity Method
    private Doctor getDoctorEntityById(Long id){

        return doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "+ id));
    }

    //Helper Method (Convert Entity -> ResponseDTO)
    private DoctorResponseDTO mapToDTO(Doctor doctor){

        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getGender(),
                doctor.getEmail(),
                doctor.getQualification(),
                doctor.getExperienceYears(),
                doctor.getSpecialization()
        );
    }

    //Create new Doctor and return DoctorResponseDTO after saving
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO){
        Doctor doctor = new Doctor();

        doctor.setName(requestDTO.getName());
        doctor.setGender(requestDTO.getGender());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setQualification(requestDTO.getQualification());
        doctor.setExperienceYears(requestDTO.getExperienceYears());
        doctor.setSpecialization(requestDTO.getSpecialization());

        Doctor savedDoctor = doctorRepository.save(doctor);

        logger.info("Doctor created with ID: {}", savedDoctor.getId());

        return mapToDTO(savedDoctor);
    }

    //Retrieve all doctors from database and map each entity to DoctorResponseDTO
    public List<DoctorResponseDTO> getAllDoctors(){

        logger.info("Fetching all doctors");

        return doctorRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Retrieve Doctor from database by ID, then map entity to DoctorResponseDTO
    public DoctorResponseDTO getDoctorById(Long id){
        Doctor doctor = getDoctorEntityById(id);

        logger.info("Fetching doctor with ID: {}", id);

        return mapToDTO(doctor);
    }

    //Fetch existing Doctor, update fields from request DTO, save changes, and return response DTO
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO){
        Doctor existingDoctor = getDoctorEntityById(id);

        existingDoctor.setName(requestDTO.getName());
        existingDoctor.setGender(requestDTO.getGender());
        existingDoctor.setEmail(requestDTO.getEmail());
        existingDoctor.setQualification(requestDTO.getQualification());
        existingDoctor.setExperienceYears(requestDTO.getExperienceYears());
        existingDoctor.setSpecialization(requestDTO.getSpecialization());

        Doctor updatedDoctor = doctorRepository.save(existingDoctor);

        logger.info("Doctor updated with ID: {}", updatedDoctor.getId());

        return mapToDTO(updatedDoctor);
    }

    //Retrieve Doctor entity by ID and remove it from repository
    public void deleteDoctor(Long id){
        Doctor existingDoctor = getDoctorEntityById(id);

        logger.info("Deleting doctor with ID: {}", id);

        doctorRepository.delete(existingDoctor);
    }

}
