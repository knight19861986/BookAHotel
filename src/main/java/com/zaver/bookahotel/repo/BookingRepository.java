package com.zaver.bookahotel.repo;

import com.zaver.bookahotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByToDateGreaterThanEqualAndFromDateLessThanEqual(LocalDate fromDate, LocalDate toDate);

}
