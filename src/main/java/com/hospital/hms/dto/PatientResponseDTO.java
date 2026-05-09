package com.hospital.hms.dto;

public class PatientResponseDTO {

    private Long id;

    private String name;

    private String phone;

    private int age;

    private String disease;

    private String gender;

    public PatientResponseDTO() {
    }

    public PatientResponseDTO(Long id, String name, String phone, int age,
            String disease, String gender) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.disease = disease;
        this.gender = gender;
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