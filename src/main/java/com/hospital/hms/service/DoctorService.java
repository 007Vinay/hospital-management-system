package com.hospital.hms.service;

import com.hospital.hms.entity.Doctor;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    //Save Doctor
    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    //Get All Doctors
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    //Get Doctor By ID
    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: "+ id));
    }

    //Update Doctor
    public Doctor updateDoctor(Long id, Doctor doctor){
        Doctor existingDoctor = getDoctorById(id);

        existingDoctor.setName(doctor.getName());
        existingDoctor.setQualification(doctor.getQualification());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setExperienceYears(doctor.getExperienceYears());
        existingDoctor.setEmail(doctor.getEmail());

        return doctorRepository.save(existingDoctor);
    }

    //Delete Doctor
    public void deleteDoctor(Long id){
        Doctor existingDoctor = getDoctorById(id);
        doctorRepository.delete(existingDoctor);
    }
}
