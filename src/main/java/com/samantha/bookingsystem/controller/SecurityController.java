package com.samantha.bookingsystem.controller;

import com.samantha.bookingsystem.dto.AuthRequest;
import com.samantha.bookingsystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @PostMapping(value = {"/authenticate"})
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated())
            return jwtService.generateToken(authRequest.getUsername());

        throw new UsernameNotFoundException("invalid user request");
    }
}
