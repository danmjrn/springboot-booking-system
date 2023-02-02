package com.samantha.bookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @JsonIgnore
    @ManyToMany(targetEntity = Administrator.class, mappedBy = "roles")
    private Set<Administrator> administrators = new HashSet<>();

    @Column
    private String alias;

    @Column
    private String description;

    @JsonIgnore
    @ManyToMany(targetEntity = Guest.class, mappedBy = "roles")
    private Set<Guest> guests = new HashSet<>();

    @Column(unique = true)
    private String name;

    public Set<Administrator> getAdministrators() {
        return administrators;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setAdministrators(Set<Administrator> administrators) {
        this.administrators = administrators;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        if (user instanceof Guest){
            if (! this.guests.contains(user)){
                this.guests.add((Guest) user);

                user.assignRole(this);
            }
        }

        if (user instanceof Administrator){
            if (! this.administrators.contains(user)){
                this.administrators.add((Administrator) user);

                user.assignRole(this);
            }
        }
    }
}
