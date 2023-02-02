package com.samantha.bookingsystem.service;

import com.samantha.bookingsystem.entity.Administrator;
import com.samantha.bookingsystem.entity.AppUserDetails;
import com.samantha.bookingsystem.entity.Guest;
import com.samantha.bookingsystem.entity.User;
import com.samantha.bookingsystem.repository.AdministratorRepository;
import com.samantha.bookingsystem.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Guest> guestOptional = guestRepository.findByEmail(username);
        Optional<Administrator> administratorOptional = administratorRepository.findByEmail(username);

        if (guestOptional.isPresent())
            return guestOptional.map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("guest not found:" + username))
            ;

        return administratorOptional.map(AppUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("admin not found:" + username))
        ;
    }
}
