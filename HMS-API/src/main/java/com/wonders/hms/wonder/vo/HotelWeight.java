package com.wonders.hms.wonder.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HotelWeight {
    private static final List<Double> starStage = new ArrayList(
            Arrays.asList(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5)
    );

    // TODO
    // rating이 0 - 5 까지 임, 그런데 등급과 같음 확인 필요(expedia table에서)
    // Arrays.asList(10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0)
    private static final List<Double> ratingStage = new ArrayList(
            Arrays.asList(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5)
    );

    private static final List<Integer> reviewStage = new ArrayList(
            Arrays.asList(1000, 900, 700, 500, 300, 200, 100, 50, 20, 10)
    );

    private static final Double starWeight = 0.3;
    private static final Double reviewWeight = 0.2;
    private static final Double ratingWeight = 0.5;

    public static Double computeWeight(Double star, Integer reviewCount, Double rating) {

        int starScore = computeScore(starStage, star);

        int ratingScore = computeScore(ratingStage, rating);

        int reviewScore = computeScore(reviewStage, reviewCount);

        // 소수점 2자리에서 반올림
        return Math.round((starScore * starWeight + reviewScore * reviewWeight + ratingScore * ratingWeight)*100)/100.0;
    }

    private static <T extends Comparable<T>> int computeScore (List<T> stages,T source) {
        if (source == null) {
            return 0;
        }

        int tempScore = 10;

        for(T stage: stages) {
            if (source.compareTo(stage) >= 0) {
                break;
            }
            tempScore--;
        }

        return tempScore;
    }
}
