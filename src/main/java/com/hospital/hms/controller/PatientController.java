package com.hospital.hms.controller;

import com.hospital.hms.entity.Patient;
import com.hospital.hms.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    //Create Patient
    @PostMapping
    public Patient createPatient(@Valid @RequestBody Patient patient){
        return patientService.savePatient(patient);
    }

    //Get all Patients
    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    //Get patient by ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    //Update Patients
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient){
        return patientService.updatePatient(id, patient);
    }

    //Delete patient by Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient Deleted Successfully");
    }
}
