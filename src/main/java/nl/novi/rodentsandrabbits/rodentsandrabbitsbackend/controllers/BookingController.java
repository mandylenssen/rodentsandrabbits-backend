package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import jakarta.validation.Valid;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/bookings")
public class BookingController {


    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody @Valid BookingDto dto) {
        long newBookingId = bookingService.createBooking(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBookingId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        boolean available = bookingService.isDateAvailable(startDate, endDate);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/unavailable-dates")
    public ResponseEntity<List<Date>> getUnavailableDates() {
        List<Date> unavailableDates = bookingService.getUnavailableDates();
        return ResponseEntity.ok().body(unavailableDates);
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok().body(bookings);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<List<BookingDto>> getBookingsByUsername(@PathVariable String username) {
        List<BookingDto> bookings = bookingService.getAllBookingsForUser(username);
        return ResponseEntity.ok().body(bookings);
    }

    @GetMapping("/currently-present")
    public ResponseEntity<List<BookingDto>> getCurrentlyPresentPets() {
        List<BookingDto> bookings = bookingService.getCurrentlyPresentPets();
        return ResponseEntity.ok().body(bookings);
    }

    @PutMapping
    public ResponseEntity<BookingDto> updateBooking(@RequestBody @Valid BookingDto dto) {
        bookingService.updateBooking(dto);
        return ResponseEntity.noContent().build();
    }
}
