package com.samantha.bookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Administrator extends User {

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
        name = "administrator_role",
        joinColumns = {
            @JoinColumn(name = "administrator_uuid", referencedColumnName = "uuid")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
        }
    )
    protected Set<Role> roles = new HashSet<>();

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void assignRole(Role role) {
        if (! this.roles.contains(role)) {
            this.roles.add(role);

            role.addUser(this);
        }
    }
}
