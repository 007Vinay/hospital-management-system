package com.hospital.hms.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Logger for JWT Authentication Filter
    private static final Logger logger = LoggerFactory
            .getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    private final InMemoryUserDetailsManager userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, InMemoryUserDetailsManager
            userDetailsService) {

        this.jwtUtil = jwtUtil;

        this.userDetailsService = userDetailsService;
    }

    //Filter executes once per request
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

        throws ServletException, IOException{

        //Read Authorization Header
        final String authHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        //Check Bearer token format
        if(authHeader != null && authHeader.startsWith("Bearer ")){

            jwtToken = authHeader.substring(7);

            try {

            //Extract username from token
            username = jwtUtil.extractUsername(jwtToken);

            }catch (ExpiredJwtException e) {

            logger.warn("Expired JWT token received");

            }catch (JwtException e) {

                logger.warn("Invalid JWT token received");
            }

        } else {

            logger.info("No JWT token found in request");
        }

        //Authenticate only if user not already authenticated
        if(username != null && SecurityContextHolder.getContext()
                .getAuthentication() == null){

            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            //Validate token
            if(jwtUtil.validateToken(jwtToken,
                    userDetails.getUsername())){

                logger.info("JWT token validated successfully for user: {}",
                        username);

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                        null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                //Set authenticated user
                SecurityContextHolder.getContext().setAuthentication(
                        authenticationToken);
            }
        }

        //Continue request flow
        filterChain.doFilter(request, response);
    }

}
