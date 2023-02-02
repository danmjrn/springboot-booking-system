package com.samantha.bookingsystem.controller.guest;

import com.samantha.bookingsystem.entity.Booking;
import com.samantha.bookingsystem.entity.Room;
import com.samantha.bookingsystem.service.domain.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = {"/room"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(
            value = {"/generate-rooms/"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Room>> generateRoomsByDate(
            @RequestBody Booking booking
    ) {
        return new ResponseEntity<>(roomService.generateRooms(booking.getCheckInDate(), booking.getCheckOutDate()), HttpStatus.OK);
    }
}
