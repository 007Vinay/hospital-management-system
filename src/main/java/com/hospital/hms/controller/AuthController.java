package com.hospital.hms.controller;

import com.hospital.hms.dto.LoginRequest;
import com.hospital.hms.dto.LoginResponse;
import com.hospital.hms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hospital.hms.entity.User;
import com.hospital.hms.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //Logger for AuthController
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    //Login API
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest
                                                            request){
        try{

            //Authenticate username and password
            Authentication authentication =
                    authenticationManager.authenticate(

                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword())
                    );

            //Generate token
            String token = jwtUtil.generateToken(
                    request.getUsername());

            logger.info("User logged in successfully: {}", request.getUsername());

            User user = userRepository.findByUsername(
                    request.getUsername())
                    .orElseThrow();

            String role = authentication
                    .getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            //Return token in response
            return ResponseEntity.ok(
                    new LoginResponse(token,
                            request.getUsername(),
                            role, user.getId()));

        } catch(BadCredentialsException ex){

            logger.warn("Invalid login attempt for username: {}",
                    request.getUsername());

            throw ex;
        }
    }
}
