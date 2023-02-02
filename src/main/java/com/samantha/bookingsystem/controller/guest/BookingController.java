package com.samantha.bookingsystem.controller.guest;

import com.samantha.bookingsystem.dto.AuthRequest;
import com.samantha.bookingsystem.entity.AppUserDetails;
import com.samantha.bookingsystem.entity.Booking;
import com.samantha.bookingsystem.service.JwtService;
import com.samantha.bookingsystem.service.domain.BookingService;
import com.samantha.bookingsystem.service.domain.GuestService;
import com.samantha.bookingsystem.service.domain.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/booking"})
@PreAuthorize("hasAuthority('ROLE_GUEST')")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private GuestService guestService;

    private JwtService jwtService;

    @Autowired
    private RoomService roomService;

    private String getCurrentUsername() {
        String username = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof AppUserDetails appUserDetails)
            username = appUserDetails.getUsername();

        return username;
    }

    @GetMapping(value = {""})
    public ResponseEntity<List<Booking>> showBookings() {

        String username = getCurrentUsername();

        if (username != null)
            return new ResponseEntity<>(bookingService.getGuestBookings(username), HttpStatus.OK);

        throw new UsernameNotFoundException("invalid user request");
    }

    @PostMapping(
            value = {"/generate-booking/{roomUuid}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Booking> generateBookingByRoom(@RequestBody Booking booking, @PathVariable UUID roomUuid) {
        String username = getCurrentUsername();

        if (username != null)
            return new ResponseEntity<>(bookingService.generateBooking("guest@domain.com", booking, roomUuid), HttpStatus.OK);

        throw new UsernameNotFoundException("invalid user request");
    }

    @PostMapping(
            value = {"/add-booking"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {

        if (bookingService.addBooking(booking))
            return new ResponseEntity<>("message: booking was successful", HttpStatus.OK);

        return new ResponseEntity<>("message: booking was unsuccessful", HttpStatus.OK);
    }

    @GetMapping(value = {"/cancel-booking-check/{bookingUuid}"})
    public ResponseEntity<String> cancelBookingCheck(@PathVariable UUID bookingUuid) throws ParseException {


        return new ResponseEntity<>(bookingService.cancelBookingCheck(bookingUuid), HttpStatus.OK);
    }

    @PutMapping(value = {"/cancel-booking/{bookingUuid}"})
    public ResponseEntity<String> cancelBooking(@PathVariable UUID bookingUuid) {

        if (bookingService.cancelBooking(bookingUuid))
            return new ResponseEntity<>("message: booking was successful", HttpStatus.OK);

        return new ResponseEntity<>("message: booking was unsuccessful", HttpStatus.OK);
    }
}
