package com.hospital.hms.config;

import com.hospital.hms.security.JwtAuthenticationFilter;
import com.hospital.hms.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hospital.hms.security.CustomUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    //JWT Authentication Filter Bean
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService customUserDetailsService){

        //Create JWT filter using database-based user authentication
        return new JwtAuthenticationFilter(jwtUtil,
                customUserDetailsService);
    }

    //Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:5173");

        configuration.addAllowedMethod("*");

        configuration.addAllowedHeader("*");

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
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

                .cors(cors -> {})

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

                        //Authenticated users can view doctors
                        .requestMatchers(HttpMethod.GET, "/doctors/**")
                        .authenticated()

                        //Only ADMIN can manage doctors
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
