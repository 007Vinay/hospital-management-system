package com.hospital.hms.controller;

import com.hospital.hms.dto.PatientRequestDTO;
import com.hospital.hms.dto.PatientResponseDTO;
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
    public PatientResponseDTO createPatient(
            @Valid
            @RequestBody PatientRequestDTO requestDTO){
        return patientService.createPatient(requestDTO);
    }

    //Get all Patients
    @GetMapping
    public List<PatientResponseDTO> getAllPatients(){
        return patientService.getAllPatients();
    }

    //Get Patient by ID
    @GetMapping("/{id}")
    public PatientResponseDTO getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    //Update Patient
    @PutMapping("/{id}")
    public PatientResponseDTO updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO requestDTO){
        return patientService.updatePatient(id, requestDTO);
    }

    //Delete Patient by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient Deleted Successfully");
    }

    // Get Patient By Phone
    @GetMapping("/phone/{phone}")
    public PatientResponseDTO getPatientByPhone(@PathVariable
                                                    String phone){

        return patientService.getPatientByPhone(phone);
    }

}
