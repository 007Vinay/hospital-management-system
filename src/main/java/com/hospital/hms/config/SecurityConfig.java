package com.hospital.hms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //In-Memory User Authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            PasswordEncoder passwordEncoder){

        UserDetails admin = User.builder()
                .username("admin")

                // Encrypt password Using BCrypt
                .password(
                        passwordEncoder.encode("admin123"))

                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    //Security Configuration for Rest APIs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                //Configure authorization rules
                .authorizeHttpRequests(auth -> auth

                        //All requests require authentication
                                .anyRequest().authenticated()
                )
                //Enable Basic Authentication
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
