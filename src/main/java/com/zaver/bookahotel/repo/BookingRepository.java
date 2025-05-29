package com.zaver.bookahotel.repo;

import com.zaver.bookahotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByFromDateGreaterThanEqualAndToDateLessThanEqual(Long fromDate, Long toDate);

}
