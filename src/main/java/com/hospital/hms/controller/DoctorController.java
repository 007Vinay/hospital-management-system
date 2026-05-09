package com.hospital.hms.controller;

import com.hospital.hms.dto.DoctorRequestDTO;
import com.hospital.hms.dto.DoctorResponseDTO;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    //Create Doctor
    @PostMapping
    public DoctorResponseDTO createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO){
        return doctorService.createDoctor(requestDTO);
    }

    //Get All Doctors
    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    //Get Doctor By ID
    @GetMapping("/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id);
    }

    //Update Doctor
    @PutMapping("/{id}")
    public DoctorResponseDTO updateDoctor(@PathVariable Long id,
                               @Valid @RequestBody DoctorRequestDTO requestDTO){

        return doctorService.updateDoctor(id, requestDTO);
    }

    //Delete Doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor Deleted Successfully");
    }
}
