package com.hospital.hms.service;

import com.hospital.hms.dto.DoctorRequestDTO;
import com.hospital.hms.dto.DoctorResponseDTO;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.entity.Patient;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

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

        return mapToDTO(savedDoctor);
    }

    //Retrieve all doctors from database and map each entity to DoctorResponseDTO
    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Retrieve Doctor from database by ID, then map entity to DoctorResponseDTO
    public DoctorResponseDTO getDoctorById(Long id){
        Doctor doctor = getDoctorEntityById(id);

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

        return mapToDTO(updatedDoctor);
    }

    //Retrieve Doctor entity by ID and remove it from repository
    public void deleteDoctor(Long id){
        Doctor existingDoctor = getDoctorEntityById(id);
        doctorRepository.delete(existingDoctor);
    }

}
