package com.hospital.hms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone Number must be 10 digits")
    private String phone;

    @Min(value = 0,
            message = "Age must be positive")
    private int age;

    private String disease;

    @NotBlank(message = "Gender is required")
    private String gender;

    //Constructor
    public PatientRequestDTO() {
    }

    public PatientRequestDTO(String name, String phone, int age, String disease, String gender) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.disease = disease;
        this.gender = gender;
    }

    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}