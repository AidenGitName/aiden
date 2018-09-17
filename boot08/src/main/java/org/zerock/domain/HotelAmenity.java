package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "hotel_amenities")
public class HotelAmenity {

    @Id
    private int idx;

    private Long hotelId;

    private  String propertyName;
    private  String tanslateName;

}
