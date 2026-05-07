package com.hospital.hms.service;

import com.hospital.hms.entity.Patient;
import com.hospital.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.hms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    //Save Patient
    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }

    //Get all patients
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    //Get Patient by ID
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    //Get Patient by Name
    public List<Patient> getPatientsByName(String name){
        return patientRepository.findByName(name);
    }

    //Update Patients
    public Patient updatePatient(Long id, Patient patient){
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: "+ id));

    existingPatient.setName(patient.getName());
    existingPatient.setAge(patient.getAge());
    existingPatient.setDisease(patient.getDisease());
    existingPatient.setGender(patient.getGender());

    return patientRepository.save(existingPatient);
    }

    //Delete Patient by ID
    public void deletePatient(Long id){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: "+ id));
        patientRepository.delete(patient);
    }
}
