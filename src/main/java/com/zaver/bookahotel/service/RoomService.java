package com.zaver.bookahotel.service;

import com.zaver.bookahotel.DTO.RoomDTO;
import com.zaver.bookahotel.DTO.mapper.DTOMapper;
import com.zaver.bookahotel.model.Booking;
import com.zaver.bookahotel.model.Room;
import com.zaver.bookahotel.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repo;

    public List<RoomDTO> getAllRooms() {
        return repo.findAll()
                .stream()
                .map(DTOMapper::mapRoomToDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAvailableRooms(Long dateFrom, Long dateTo, Integer numberOfBeds) {
        if (dateFrom >= dateTo) {
            throw new IllegalArgumentException("fromDate must be earlier than toDate");
        }

        List<Room> rooms = (numberOfBeds != null) ?
                repo.findByNumberOfBeds(numberOfBeds) : repo.findAll();

        return rooms
                .stream()
                .filter(room -> roomIsAvailable(room, dateFrom, dateTo))
                .map(DTOMapper::mapRoomToDTO)
                .collect(Collectors.toList());
    }

    //Not correct
    public static boolean roomIsAvailable(Room room, Long dateFrom, Long dateTo) {
        for (Booking booking : room.getBookings()) {
            if (dateTo > booking.getFromDate() || dateFrom < booking.getToDate()) {
                return false;
            }
        }
        return true;
    }
}
