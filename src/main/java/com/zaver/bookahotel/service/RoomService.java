package com.zaver.bookahotel.service;

import com.zaver.bookahotel.model.Room;
import com.zaver.bookahotel.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repo;

    public List<Room> getAllRooms() {
        return repo.findAll();
    }

    public List<Room> getAvailableRooms(long dateFrom, long dateTo, Long numberOfBeds) {
        return repo.findByNumberOfBeds(numberOfBeds);
    }
}
