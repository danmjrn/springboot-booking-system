package com.samantha.bookingsystem.service.domain;

import com.samantha.bookingsystem.entity.Booking;
import com.samantha.bookingsystem.entity.Guest;
import com.samantha.bookingsystem.entity.Room;
import com.samantha.bookingsystem.repository.BookingRepository;
import com.samantha.bookingsystem.repository.GuestRepository;
import com.samantha.bookingsystem.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService extends com.samantha.bookingsystem.service.Service {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    private double calculateBookingFee(Date checkInDate, Date checkOutDate){
        long bookingDays = Math.abs(checkOutDate.getTime() - checkInDate.getTime());

        bookingDays = TimeUnit.DAYS.convert(bookingDays, TimeUnit.MILLISECONDS) + 1;

        return (bookingDays + Booking.RATE_DIVIDEND) / Booking.RATE_DIVISOR;
    }

    private double calculateCancellationFee(Date checkInDate, double bookingFee) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

        int bookingDaysBefore = (int) Math.abs((checkInDate.getTime() - currentDate.getTime()));

        bookingDaysBefore = (int) TimeUnit.DAYS.convert(bookingDaysBefore, TimeUnit.MILLISECONDS) + 1;

        if (bookingDaysBefore >= 14)
            return bookingFee;

        if (bookingDaysBefore >= 7)
            return (bookingFee* .5);

        return 0;
    }

    private double calculateReschedulingFee(Date checkInDate, double bookingFee) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

        int bookingDaysBefore = (int) Math.abs((checkInDate.getTime() - currentDate.getTime()));

        bookingDaysBefore = (int) TimeUnit.DAYS.convert(bookingDaysBefore, TimeUnit.MILLISECONDS) + 1;

        if (bookingDaysBefore >= 14)
            return 0.00;

        if (bookingDaysBefore >= 7)
            return bookingFee - (bookingFee * .5);

        return bookingFee;
    }

    private boolean isRoomAvailableForBooking(Booking booking) {
        Optional<Booking> bookingOptional1 = bookingRepository.findByGuestUuidAndRoomUuidAndCheckInDate(
            booking.getGuest().getUuid(),
            booking.getRoom().getUuid(),
            booking.getCheckInDate()
        );

        Optional<Booking> bookingOptional2 = bookingRepository.findByGuestUuidAndRoomUuidAndCheckOutDate(
                booking.getGuest().getUuid(),
                booking.getRoom().getUuid(),
                booking.getCheckOutDate()
        );

        return bookingOptional1.isEmpty() && bookingOptional2.isEmpty();
    }

    @Transactional
    public boolean addBooking(Booking booking) {
        boolean check = isRoomAvailableForBooking(booking);

        if (check) {
            bookingRepository.save(booking);
            return true;
        }

        return false;
    }

    public List<Booking> getGuestBookings(String guestEmail) {
        Optional<List<Booking>> optionalBookings = bookingRepository.findByGuestEmail(guestEmail);

        return optionalBookings.orElse(null);
    }

    public Booking generateBooking(String userEmail, Booking booking, UUID roomUuid) {
        Optional<Guest> guestOptional = guestRepository.findByEmail(userEmail);
        Guest guest;

        Optional<Room> roomOptional = roomRepository.findByUuid(roomUuid);
        Room room;

        if(guestOptional.isPresent() && roomOptional.isPresent()){
            guest = guestOptional.get();

            room = roomOptional.get();

            booking.setBookingFee(
                calculateBookingFee(
                    booking.getCheckInDate(),
                    booking.getCheckOutDate()
                )
            );

            booking.setGuest(guest);

            booking.setRoom(room);
        }

        return booking;
    }

    public boolean cancelBooking(UUID bookingUuid) {
        Optional<Booking> optionalBooking = bookingRepository.findByUuid(bookingUuid);

        Booking booking;

        if (optionalBooking.isPresent()){
            booking = optionalBooking.get();

            booking.setCancelled(true);

            bookingRepository.save(booking);

            return true;
        }
        return false;
    }

    public void rescheduleBooking() {}

    public String cancelBookingCheck(UUID bookingUuid) throws ParseException {
        Optional<Booking> optionalBooking = bookingRepository.findByUuid(bookingUuid);

        Booking booking;

        if (optionalBooking.isPresent()){
            booking = optionalBooking.get();

            return "Cancellation fee is:" + calculateCancellationFee(booking.getCheckInDate(), booking.getBookingFee());
        }
        return null;
    }
}
