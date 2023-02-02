package com.samantha.bookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @Column
    private double bookingFee;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date checkOutDate;

    @ManyToOne
    private Guest guest;

    @Column
    private boolean isCancelled = false;

    public static final double RATE_DIVIDEND = 100;

    public static final double RATE_DIVISOR = 12;

    @ManyToOne
    private Room room;

    public UUID getUuid() {
        return uuid;
    }

    public double getBookingFee() {
        return bookingFee;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public Room getRoom() {
        return room;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setBookingFee(double bookingFee) {
        this.bookingFee = bookingFee;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
