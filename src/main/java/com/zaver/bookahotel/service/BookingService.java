package com.zaver.bookahotel.service;

import com.zaver.bookahotel.DTO.BookingDTO;
import com.zaver.bookahotel.DTO.mapper.DTOMapper;
import com.zaver.bookahotel.DTO.request.BookRequest;
import com.zaver.bookahotel.exception.RoomIsNotAvailableException;
import com.zaver.bookahotel.model.Booking;
import com.zaver.bookahotel.model.Room;
import com.zaver.bookahotel.repo.BookingRepository;
import com.zaver.bookahotel.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    //Not correct
    public List<BookingDTO> getBookingsWithinRange(LocalDate fromDate, LocalDate toDate) {
        if (!fromDate.isBefore(toDate)) {
            throw new IllegalArgumentException("fromDate must be earlier than toDate");
        }

        return bookingRepository.findByFromDateGreaterThanEqualAndToDateLessThanEqual(fromDate, toDate)
                .stream().map(DTOMapper::mapBookingToDTO).collect(Collectors.toList());
    }

    public Long createBooking(BookRequest request) {
        if (!request.getFromDate().isBefore(request.getToDate())) {
            throw new IllegalArgumentException("fromDate must be earlier than toDate");
        }

        Set<Room> rooms = new HashSet<>(roomRepository.findAllById(request.getRoomIds()));

        if (rooms.size() != request.getRoomIds().size()) {
            throw new IllegalArgumentException("One or more room IDs are invalid");
        }

        LocalDate fromDate = request.getFromDate();
        LocalDate toDate = request.getToDate();

        for(Room room:rooms){
            if(!RoomService.roomIsAvailable(room, fromDate, toDate))
                throw new RoomIsNotAvailableException("Room " + room.getRoomId() + " is not available");
        }

        Booking booking = new Booking();
        booking.setFromDate(fromDate);
        booking.setToDate(toDate);
        booking.setRooms(rooms);

        Booking saved = bookingRepository.save(booking);
        return saved.getBookId();
    }

    public void cancelBooking(long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
        }
        bookingRepository.deleteById(bookingId);
    }
}
