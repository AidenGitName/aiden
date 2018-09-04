package com.wonders.hms.place.google.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearbyTouristAttraction {
    private String name;
    private String type;
    private String icon;
    private Double distance;
}
