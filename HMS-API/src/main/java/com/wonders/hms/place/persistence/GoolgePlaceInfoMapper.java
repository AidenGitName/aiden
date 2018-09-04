package com.wonders.hms.place.persistence;

import org.apache.ibatis.annotations.Param;

public interface GoolgePlaceInfoMapper {
    void insertGooglePlaceInfo(@Param("key") String key, @Param("value") String value);

    String getGooglePlaceInfo(String key);

    void updateGooglePlaceInfoTimeAndHit(String key);

    void deleteGooglePlaceInfo(String key);
}
