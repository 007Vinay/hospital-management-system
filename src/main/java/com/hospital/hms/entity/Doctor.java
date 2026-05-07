package com.hospital.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Doctor {

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Appointment> appointments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    private String name;
    private String email;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @Min(value = 0, message = "Experience must be positive")
    private int experienceYears;

    private String specialization;

    public Doctor() {
    }

    public Doctor(String name, String email, String qualification, int experienceYears, String specialization) {
        this.name = name;
        this.email = email;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
        this.specialization = specialization;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
