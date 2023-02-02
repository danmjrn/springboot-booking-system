package com.samantha.bookingsystem;

import com.samantha.bookingsystem.entity.Administrator;
import com.samantha.bookingsystem.entity.Role;
import com.samantha.bookingsystem.repository.AdministratorRepository;
import com.samantha.bookingsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public List<Administrator> index() {

        return administratorRepository.findAll();
    }

    @GetMapping("/calender")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public String calendar() {
        return "app-calender";
    }
}
