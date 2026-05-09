package com.hospital.hms.config;

import com.hospital.hms.security.JwtAuthenticationFilter;
import com.hospital.hms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    //JWT Authentication Filter Bean
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtUtil jwtUtil,
            InMemoryUserDetailsManager userDetailsService){

        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

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
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception{
        http
                //Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                //Stateless session for JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                //Configure authorization rules
                .authorizeHttpRequests(auth -> auth

                        //Swagger URLs
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs")
                        .permitAll()

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
                );

        //Add JWT filter before Spring authentication filter
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

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
