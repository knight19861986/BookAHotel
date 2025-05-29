package com.zaver.bookahotel.controllers;

import com.zaver.bookahotel.DTO.request.BookRequest;
import com.zaver.bookahotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zaver.bookahotel.repo.RoomRepository;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Controller {

    private final BookingService bookingService;

    @GetMapping("/rooms")
    public List<Long> searchRooms(@RequestParam(name = "dateFrom") long dateFrom,
                                  @RequestParam(name = "dateTo") long dateTo,
                                  @RequestParam(name = "numberOfBeds") int numberOfBeds) {

        return null;
    }

    @PostMapping("/book")
    public ResponseEntity<Long> bookRoom(@Valid @RequestBody BookRequest request) {
        Long bookId = bookingService.createBooking(request);
        return ResponseEntity.ok(bookId);
    }

    @GetMapping("/bookings")
    public List<Long> searchBookings(@RequestParam(name = "dateFrom") long dateFrom,
                                     @RequestParam(name = "dateTo") long dateTo) {

        return null;
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
