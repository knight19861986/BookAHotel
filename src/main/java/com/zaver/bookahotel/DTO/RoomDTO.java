package com.zaver.bookahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {
    private Long roomId;
    private Integer numberOfBeds;
    private Float price;
}
