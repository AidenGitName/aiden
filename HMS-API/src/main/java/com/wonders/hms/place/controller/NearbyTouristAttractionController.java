package com.wonders.hms.place.controller;

import com.wonders.hms.config.URIMapping;
import com.wonders.hms.place.google.service.NearbySearchService;
import com.wonders.hms.place.google.vo.NearbySearchVO;
import com.wonders.hms.place.google.vo.NearbyTouristAttraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(URIMapping.BASE_URI + "/nearby_tourist_attraction")
public class NearbyTouristAttractionController {

    @Autowired
    NearbySearchService nearbySearchService;

    @GetMapping
    @ResponseBody
    public List<NearbyTouristAttraction> nearbyTouristAttraction(NearbySearchVO nearbySearchVO) throws Exception {
        return nearbySearchService.nearbySearch(nearbySearchVO);
    }
}
