package com.zaver.bookahotel.controllers;

import com.zaver.bookahotel.DTO.BookingDTO;
import com.zaver.bookahotel.DTO.RoomDTO;
import com.zaver.bookahotel.DTO.request.BookRequest;
import com.zaver.bookahotel.service.BookingService;
import com.zaver.bookahotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> searchRooms(
            @RequestParam(name = "dateFrom", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateFrom,
            @RequestParam(name = "dateTo", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateTo,
            @RequestParam(name = "numberOfBeds", required = false)
                    Integer numberOfBeds) {
        if (dateFrom == null)
            dateFrom = LocalDate.now();
        if (dateTo == null)
            dateTo = LocalDate.MAX;

        return ResponseEntity.ok(roomService.getAvailableRooms(dateFrom, dateTo, numberOfBeds));
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> searchBookings(
            @RequestParam(name = "dateFrom", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateFrom,
            @RequestParam(name = "dateTo", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateTo) {
        if (dateFrom == null)
            dateFrom = LocalDate.now();
        if (dateTo == null)
            dateTo = LocalDate.now().plusYears(100);
        return ResponseEntity.ok(bookingService.getBookingsWithinRange(dateFrom, dateTo));
    }

    @PostMapping("/book")
    public ResponseEntity<Long> bookRoom(@Valid @RequestBody BookRequest request) {
        Long bookId = bookingService.createBooking(request);
        return ResponseEntity.ok(bookId);
    }

    @DeleteMapping("/book/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
