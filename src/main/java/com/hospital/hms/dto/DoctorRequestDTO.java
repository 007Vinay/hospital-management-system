package com.hospital.hms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class DoctorRequestDTO {

    @NotBlank(message = "Doctor name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String email;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @Min(value = 0, message = "Experience must be positive")
    private int experienceYears;

    private String specialization;

    // Default Constructor
    public DoctorRequestDTO() {
    }

    // Parameterized Constructor
    public DoctorRequestDTO(String name,
                            String gender,
                            String email,
                            String qualification,
                            int experienceYears,
                            String specialization) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
        this.specialization = specialization;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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