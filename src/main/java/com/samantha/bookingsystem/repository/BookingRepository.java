package com.samantha.bookingsystem.repository;

import com.samantha.bookingsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByGuestUuidAndRoomUuidAndCheckInDate(UUID guestUuid, UUID roomUuid, Date checkInDate);

    Optional<Booking> findByGuestUuidAndRoomUuidAndCheckOutDate(UUID guestUuid, UUID roomUuid, Date checkOutDate);

    Optional<List<Booking>> findByGuestUuid(UUID guestUuid);

    Optional<List<Booking>> findByGuestEmail(String guestEmail);

    Optional<Booking> findByUuid(UUID bookingUuid);
}
