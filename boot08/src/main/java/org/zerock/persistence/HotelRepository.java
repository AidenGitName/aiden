package org.zerock.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.HotelAmenity;

import java.util.List;

public interface HotelRepository extends CrudRepository<HotelAmenity, Integer> {

    @Query("SELECT info.hotelId FROM HotelInfo info WHERE info.hotelId NOT IN (SELECT amenity.hotelId FROM HotelAmenity amenity) AND info.hotelId>2693938")
    List<Integer> getAllbyHotelIds();
}
