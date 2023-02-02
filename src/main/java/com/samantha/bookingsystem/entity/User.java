package com.samantha.bookingsystem.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    protected UUID uuid;

    @Column
    protected String email;

    @Column
    protected String firstName;

    @Column
    protected boolean isDeleted;

    @Column
    protected boolean isVerified;

    @Column
    protected String lastName;

    @Column
    protected String password;

    @ManyToMany(targetEntity = Role.class)
    protected Set<Role> roles = new HashSet<>();

    @Column
    protected String username;

    public void assignRole(Role role) {
        if (! this.roles.contains(role)) {
            this.roles.add(role);

            role.addUser(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
