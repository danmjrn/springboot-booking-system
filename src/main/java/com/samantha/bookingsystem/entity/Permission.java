package com.samantha.bookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @Column
    private String description;

    @Column(unique = true)
    private String name;

    @Column
    private String type;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
        name = "permission_role",
        joinColumns = {
            @JoinColumn(name = "permission_uuid", referencedColumnName = "uuid")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
        }
    )
    private Set<Role> roles = new HashSet<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
