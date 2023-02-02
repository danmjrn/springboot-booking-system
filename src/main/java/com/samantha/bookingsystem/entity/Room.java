package com.samantha.bookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @Column(unique = true)
    private String alias;

    @JsonIgnore
    @OneToMany(targetEntity = Booking.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_uuid", referencedColumnName = "uuid")
    private List<Booking> bookings;

    @Column
    private int number;

    @Enumerated(EnumType.STRING)
    @Column
    private RoomType type = RoomType.ONE_BEDROOM;

    public String getAlias() {
        return alias;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(RoomType type) {
        this.type = type;
    }
}
