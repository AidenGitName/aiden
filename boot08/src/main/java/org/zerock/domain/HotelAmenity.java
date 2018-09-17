package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "hotel_amenities")
public class HotelAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    private Long hotelId;

    private  String propertyName;
    private  String tanslateName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HotelAmenity){
            HotelAmenity temp = (HotelAmenity)obj;
            return hotelId.equals(temp.hotelId) && propertyName.equals(temp.propertyName) && tanslateName.equals(temp.tanslateName);
        }
        return false;
    }
    public int hashCode(){
        return (hotelId + propertyName + tanslateName).hashCode();
    }
}
