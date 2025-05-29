package com.zaver.bookahotel.service;

import com.zaver.bookahotel.DTO.BookingDTO;
import com.zaver.bookahotel.DTO.mapper.DTOMapper;
import com.zaver.bookahotel.DTO.request.BookRequest;
import com.zaver.bookahotel.model.Booking;
import com.zaver.bookahotel.model.Room;
import com.zaver.bookahotel.repo.BookingRepository;
import com.zaver.bookahotel.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BookingDTO> getBookingsWithinRange(Long fromInclusive, Long toInclusive) {
        return bookingRepository.findByFromDateGreaterThanEqualAndToDateLessThanEqual(fromInclusive, toInclusive)
                .stream().map(DTOMapper::mapBookingToDTO).collect(Collectors.toList());
    }


    public Long createBooking(BookRequest request) {
        if (request.getFromDate() >= request.getToDate()) {
            throw new IllegalArgumentException("fromDate must be earlier than toDate");
        }

        Set<Room> rooms = new HashSet<>(roomRepository.findAllById(request.getRoomIds()));

        if (rooms.size() != request.getRoomIds().size()) {
            throw new IllegalArgumentException("One or more room IDs are invalid");
        }

        Booking booking = new Booking();
        booking.setFromDate(request.getFromDate());
        booking.setToDate(request.getToDate());
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
