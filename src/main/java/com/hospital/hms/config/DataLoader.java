package com.hospital.hms.config;

import com.hospital.hms.entity.User;

import com.hospital.hms.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadUsers(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            //Create admin user if not exists
            if (userRepository
                    .findByUsername("admin")
                    .isEmpty()) {

                User admin = new User(
                        "admin",
                        passwordEncoder.encode(
                                "admin123"),
                        "ADMIN"
                );
                userRepository.save(admin);
            }

            //Create doctor user if not exists
            if (userRepository
                    .findByUsername("doctor")
                    .isEmpty()) {

                User doctor = new User(
                        "doctor",
                        passwordEncoder.encode(
                                "doctor123"),
                        "DOCTOR"
                );
                userRepository.save(doctor);
            }
        };
    }
}