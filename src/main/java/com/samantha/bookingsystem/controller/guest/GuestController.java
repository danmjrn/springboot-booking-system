package com.samantha.bookingsystem.controller.guest;

import com.samantha.bookingsystem.entity.AppUserDetails;
import com.samantha.bookingsystem.entity.Booking;
import com.samantha.bookingsystem.entity.Guest;
import com.samantha.bookingsystem.service.StorageService;
import com.samantha.bookingsystem.service.domain.GuestService;
import jakarta.persistence.PersistenceException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = {"/guest"})
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    private StorageService storageService;

    private String getCurrentUsername() {
        String username = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof AppUserDetails appUserDetails)
            username = appUserDetails.getUsername();

        return username;
    }

    @GetMapping(value = {""})
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Guest> showGuest() {

        String username = getCurrentUsername();

        if (username != null)
            return new ResponseEntity<>(guestService.getGuest(username), HttpStatus.OK);

        throw new UsernameNotFoundException("invalid user request");
    }

    @PostMapping(
            value = {"/add-guest"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> addGuest(@RequestBody Guest guest) {

        try {
            guestService.addGuest(guest);

            return new ResponseEntity<>("message: added guest successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("message: did not save guest. guest already exists.", HttpStatus.OK);
        }
    }

    @PostMapping(
            value = {"profile/upload"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "multipartFile") MultipartFile multipartFile) {
        return new ResponseEntity<>(storageService.uploadFile(multipartFile), HttpStatus.OK);
    }

    @GetMapping(value = {"profile/download/{fileName}"})
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource arrayResource =new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(arrayResource);
    }

    @DeleteMapping(value = {"profile/delete/{fileName}"})
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }
}
