package com.zaver.bookahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BookingDTO {
    private Long bookId;
    private Long fromDate;
    private Long toDate;
    private Set<RoomDTO> rooms;
}
