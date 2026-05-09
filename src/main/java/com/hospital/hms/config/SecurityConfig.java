package com.hospital.hms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Security Configuration for Rest APIs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                //Configure authorization rules
                .authorizeHttpRequests(auth -> auth

                        //Allow all requests temporarily
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}
