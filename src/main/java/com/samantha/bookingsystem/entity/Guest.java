package com.samantha.bookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends User{

    @JsonIgnore
    @OneToMany(targetEntity = Booking.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_uuid", referencedColumnName = "uuid")
    private List<Booking> bookings;

    @Column(length = 13)
    private String idNumber;

    @Column(length = 50)
    private String phoneNumber;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
        name = "guest_role",
        joinColumns = {
            @JoinColumn(name = "guest_uuid", referencedColumnName = "uuid")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
        }
    )
    protected Set<Role> roles = new HashSet<>();

    @Override
    public void assignRole(Role role) {
        if (! this.roles.contains(role)) {
            this.roles.add(role);

            role.addUser(this);
        }
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
