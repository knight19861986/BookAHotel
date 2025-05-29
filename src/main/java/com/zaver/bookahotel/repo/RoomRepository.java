package com.zaver.bookahotel.repo;

import com.zaver.bookahotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByNumberOfBeds(Integer numberOfBeds);

}
