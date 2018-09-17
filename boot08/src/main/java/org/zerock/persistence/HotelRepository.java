package org.zerock.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.HotelAmenity;
import org.zerock.domain.HotelInfo;

import java.util.List;

public interface HotelRepository extends CrudRepository<HotelAmenity, Integer> {

    @Query
    List<Integer> getAllByHotelId();

}
