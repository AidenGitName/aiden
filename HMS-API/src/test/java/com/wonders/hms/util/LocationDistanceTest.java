package com.wonders.hms.util;

import org.junit.Test;

import static com.wonders.hms.util.LocationDistance.*;

public class LocationDistanceTest {

    @Test
    public void locationDistanceTest() {
        System.out.println(distance(37.504198, 127.047967, 37.501025, 127.037701, LocationDistanceUnit.MILE));

        // 미터(Meter) 단위
        System.out.println(distance(37.5618924566236, 126.981161006443, 37.5618924566236, 126.981161006443, LocationDistanceUnit.METER));

        System.out.println(distance(37.562115, 126.981506, 37.562099, 126.981713, LocationDistanceUnit.METER));

        // 킬로미터(Kilo Meter) 단위
        System.out.println(distance(37.504198, 127.047967, 37.501025, 127.037701, LocationDistanceUnit.KILOMETER));


    }
}
