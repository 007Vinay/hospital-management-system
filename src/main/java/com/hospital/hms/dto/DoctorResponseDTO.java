package com.hospital.hms.dto;

public class DoctorResponseDTO {

    private Long id;
    private String name;
    private String gender;
    private String email;
    private String qualification;
    private int experienceYears;
    private String specialization;

    // Default Constructor
    public DoctorResponseDTO() {
    }

    // Parameterized Constructor
    public DoctorResponseDTO(Long id,
                             String name,
                             String gender,
                             String email,
                             String qualification,
                             int experienceYears,
                             String specialization) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
        this.specialization = specialization;
    }

    // Getters and Setters
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