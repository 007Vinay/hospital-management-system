package com.hospital.hms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

        //Admin User
        UserDetails admin = User.builder()
                .username("admin")
                .password(
                        passwordEncoder.encode("admin123"))

                .roles("ADMIN")
                .build();

        //Doctor User
        UserDetails doctor = User.builder()
                .username("doctor")
                .password(
                        passwordEncoder.encode("doctor123"))

                .roles("DOCTOR")
                .build();

        return new InMemoryUserDetailsManager(admin, doctor);
    }

    //Security Configuration for Rest APIs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                //Configure authorization rules
                .authorizeHttpRequests(auth -> auth

                        //Public login API
                        .requestMatchers("/auth/**")
                        .permitAll()

                        //Only ADMIN can access doctor APIs
                        .requestMatchers("/doctors/**")
                        .hasRole("ADMIN")

                        //ADMIN and DOCTOR both can access appointments
                        .requestMatchers("/appointments/**")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        //Any authenticated user can access patients
                        .requestMatchers("/patients/**")
                        .authenticated()

                        //Any other request requires authentication
                        .anyRequest()
                        .authenticated()
                )
                //Enable Basic Authentication
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    //Authentication Manager Bean
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
        throws Exception{

        return config.getAuthenticationManager();
    }
}
