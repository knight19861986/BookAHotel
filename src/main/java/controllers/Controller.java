package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repo.RoomRepository;

import java.util.List;


@RestController
@RequestMapping("/v1")
public class Controller {

    @Autowired
    private RoomRepository RoomRepo;

    @GetMapping("/SearchRooms")
    public List<Long> searchRooms(@RequestParam(name = "date") long date,
                                  @RequestParam(name = "numberOfBeds")int numberOfBeds) {

        return null;
    }

    @PostMapping("/BookRoom")
    public long bookRoom(@RequestParam(name = "roomId") long roomId) {

        return 0;
    }

    @GetMapping("/SearchRooms")
    public List<Long> searchBookings(@RequestParam(name = "date") long date) {

        return null;
    }

    @PostMapping("/cancelBooking")
    public void cancelBooking(@RequestParam(name = "bookingId") long bookingId) {


    }
}
