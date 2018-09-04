package com.wonders.hms.place.google.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.hms.place.google.vo.NearbyTouristAttraction;
import com.wonders.hms.place.persistence.GoolgePlaceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class GooglePlaceInfoCache {
    @Autowired
    GoolgePlaceInfoMapper goolgePlaceInfoMapper;

    @Autowired
    ObjectMapper objectMapper;

    public List<NearbyTouristAttraction> get(String key) throws IOException {
        String value = goolgePlaceInfoMapper.getGooglePlaceInfo(key);

        goolgePlaceInfoMapper.updateGooglePlaceInfoTimeAndHit(key);

        if (value == null) {
            return null;
        }

        return this.objectMapper.readValue(
                value,
                new TypeReference<List<NearbyTouristAttraction>>() { }
        );
    }

    public boolean set(String key, List<NearbyTouristAttraction> nearbyTouristAttractions) {
        try {
            String value = this.objectMapper.writeValueAsString(nearbyTouristAttractions);
            goolgePlaceInfoMapper.insertGooglePlaceInfo(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    public boolean set(String key) {
        return delete(key);
    }

    public boolean delete(String key) {
        try {
            goolgePlaceInfoMapper.deleteGooglePlaceInfo(key);
            return true;
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }
}
