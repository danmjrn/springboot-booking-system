package com.samantha.bookingsystem.command;

import com.samantha.bookingsystem.entity.*;
import com.samantha.bookingsystem.repository.AdministratorRepository;
import com.samantha.bookingsystem.repository.GuestRepository;
import com.samantha.bookingsystem.repository.RoleRepository;
import com.samantha.bookingsystem.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class InitializeDataSourceDataCommand {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public void initializeRoles() {

        Optional<Role> optionalRole = roleRepository.findByName("User");
        Role role = new Role();

        if (optionalRole.isEmpty()){
            role.setName("User");
            role.setAlias("ROLE_USER");
            role.setDescription("Application User.");

            roleRepository.save(role);
        }

        optionalRole = roleRepository.findByName("Super Admin");
        role = new Role();

        if (optionalRole.isEmpty()) {
            role.setName("Super Admin");
            role.setAlias("ROLE_SUPER_ADMIN");
            role.setDescription("Application Super Admin.");

            roleRepository.save(role);
        }

        optionalRole = roleRepository.findByName("Admin");
        role = new Role();

        if (optionalRole.isEmpty()) {
            role.setName("Admin");
            role.setAlias("ROLE_ADMIN");
            role.setDescription("Application Super Admin.");

            roleRepository.save(role);
        }

        optionalRole = roleRepository.findByName("Guest");
        role = new Role();

        if (optionalRole.isEmpty()) {
            role.setName("Guest");
            role.setAlias("ROLE_GUEST");
            role.setDescription("Application Guest.");

            roleRepository.save(role);
        }
    }

    @Transactional
    public void initializeAdmins() {
        String email1 = "daniel@booking.com";
        String firstName1 = "Daniel";
        boolean isDeleted1 = false;
        boolean isVerified1 = true;
        String lastName1 = "Nkulu";
        String password1 = this.passwordEncoder.encode("Testing1234");

        Optional<Role> optionalRole1 = roleRepository.findByName("Super Admin");
        Role role1 = optionalRole1.orElse(new Role());

        String email2 = "admin2@booking.com";
        String firstName2 = "Admin2";
        boolean isDeleted2 = false;
        boolean isVerified2 = true;
        String lastName2 = "Nimda2";
        String password2 = this.passwordEncoder.encode("Testing1234");

        Optional<Role> optionalRole2 = roleRepository.findByName("Admin");
        Role role2 = optionalRole2.orElse(new Role());

        Optional<Administrator> optionalAdministrator = administratorRepository.findByEmail(email1);
        Administrator administrator = new Administrator();

        if (optionalAdministrator.isEmpty()) {
            administrator.setEmail(email1);
            administrator.setFirstName(firstName1);
            administrator.setDeleted(isDeleted1);
            administrator.setVerified(isVerified1);
            administrator.setLastName(lastName1);
            administrator.setPassword(password1);
            administrator.setUsername(email1);
            administrator.assignRole(role1);

            administratorRepository.save(administrator);
        }

        optionalAdministrator = administratorRepository.findByEmail(email2);
        administrator = new Administrator();

        if (optionalAdministrator.isEmpty()) {
            administrator.setEmail(email2);
            administrator.setFirstName(firstName2);
            administrator.setDeleted(isDeleted2);
            administrator.setVerified(isVerified2);
            administrator.setLastName(lastName2);
            administrator.setPassword(password2);
            administrator.setUsername(email2);
            administrator.assignRole(role2);

            administratorRepository.save(administrator);
        }
    }

    @Transactional
    public void initializeGuests() {
        String email1 = "guest@domain.com";
        String firstName1 = "Guest";
        boolean isDeleted1 = false;
        boolean isVerified1 = true;
        String lastName1 = "Booker";
        String password1 = this.passwordEncoder.encode("Testing1234");
        String idNumber1 = "9904054786086";
        String phoneNumber1 = "0813783478";

        Optional<Role> optionalRole1 = roleRepository.findByName("Guest");
        Role role1 = optionalRole1.orElse(new Role());

        Optional<Guest> optionalGuest = guestRepository.findByEmail(email1);
        Guest guest = new Guest();

        if (optionalGuest.isEmpty()) {
            guest.setEmail(email1);
            guest.setFirstName(firstName1);
            guest.setDeleted(isDeleted1);
            guest.setVerified(isVerified1);
            guest.setLastName(lastName1);
            guest.setPassword(password1);
            guest.setIdNumber(idNumber1);
            guest.setPhoneNumber(phoneNumber1);
            guest.setUsername(email1);
            guest.assignRole(role1);

            guestRepository.save(guest);
        }
    }

    public void initializeRooms() {
        Optional<Room> roomOptional = Optional.empty();
        Room room;

        String roomAlias = "Samantha Room";
        int roomCount = 5;

        for (int i = 0; i < roomCount; i++) {
            roomOptional = roomRepository.findByAlias(roomAlias + " A" + (i+1));
            room = new Room();

            if(roomOptional.isEmpty()){
                room.setAlias(roomAlias + " A" + (i+1));
                room.setNumber(i+1);
                room.setType(RoomType.ONE_BEDROOM);

                roomRepository.save(room);
            }
        }
    }
}
