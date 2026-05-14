package com.hospital.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
public class Patient {

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must be 10 digits")
    @Column(nullable = false, unique = true)
    private String phone;

    @Min(value = 0, message = "Age must be positive")
    private int age;
    private String disease;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Gender is required")
    private String gender;

    public Patient(){

    }
    public Patient(String name, String phone, int age, String disease, String gender) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.disease = disease;
        this.gender = gender;
    }

    //Getters and Setters
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
