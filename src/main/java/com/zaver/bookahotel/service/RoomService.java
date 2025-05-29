package com.zaver.bookahotel.service;

import com.zaver.bookahotel.DTO.RoomDTO;
import com.zaver.bookahotel.DTO.mapper.DTOMapper;
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
                .stream().map(DTOMapper::mapRoomToDTO).collect(Collectors.toList());
    }

    public List<RoomDTO> getAvailableRooms(Long dateFrom, Long dateTo, Integer numberOfBeds) {
        return repo.findByNumberOfBeds(numberOfBeds)
                .stream().map(DTOMapper::mapRoomToDTO).collect(Collectors.toList());
    }
}
