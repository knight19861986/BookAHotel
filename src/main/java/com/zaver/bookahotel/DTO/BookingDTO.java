package com.zaver.bookahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class BookingDTO {
    private Long bookId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Set<RoomDTO> rooms;
}
