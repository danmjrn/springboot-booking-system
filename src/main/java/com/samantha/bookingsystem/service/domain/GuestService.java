package com.samantha.bookingsystem.service.domain;

import com.samantha.bookingsystem.entity.Guest;
import com.samantha.bookingsystem.entity.Role;
import com.samantha.bookingsystem.repository.GuestRepository;
import com.samantha.bookingsystem.repository.RoleRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService extends com.samantha.bookingsystem.service.Service {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Guest getGuest(String username) {
        return guestRepository.findByEmail(username).orElse(null);
    }

    public void addGuest(Guest guest) {
        Optional<Guest> optionalGuest = Optional.empty();

        if(guest.getEmail() != null)
            optionalGuest = guestRepository.findByEmail(guest.getEmail());

        Optional<Role> optionalRole1 = roleRepository.findByName("Guest");
        Role role = optionalRole1.orElse(new Role());

        String password = passwordEncoder.encode(guest.getPassword());

        if (optionalGuest.isEmpty()) {
            guest.setDeleted(false);
            guest.setVerified(true);
            guest.setPassword(password);
            guest.assignRole(role);

            guestRepository.save(guest);
        }
        else {
            throw new PersistenceException("user already exists");
        }
    }
}
