package com.hospital.hms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

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

            //Extract username from token
            username = jwtUtil.extractUsername(jwtToken);
        }

        //Authenticate only if user not already authenticated
        if(username != null && SecurityContextHolder.getContext()
                .getAuthentication() == null){

            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            //Validate token
            if(jwtUtil.validateToken(jwtToken,
                    userDetails.getUsername())){

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
