package com.zaver.bookahotel.DTO.mapper;

import com.zaver.bookahotel.DTO.BookingDTO;
import com.zaver.bookahotel.DTO.RoomDTO;
import com.zaver.bookahotel.model.Booking;
import com.zaver.bookahotel.model.Room;

import java.util.Set;
import java.util.stream.Collectors;

public class DTOMapper {
    public static BookingDTO mapBookingToDTO(Booking booking) {
        Set<RoomDTO> roomDTOs = booking.getRooms().stream()
                .map(DTOMapper::mapRoomToDTO)
                .collect(Collectors.toSet());

        return new BookingDTO(
                booking.getBookId(),
                booking.getFromDate(),
                booking.getToDate(),
                roomDTOs
        );
    }

    public static RoomDTO mapRoomToDTO(Room room) {
        return new RoomDTO(
                room.getRoomId(),
                room.getNumberOfBeds(),
                room.getPrice()
        );
    }
}
