package com.wonders.hms.util;

import org.junit.Before;
import org.junit.Test;

public class LevenshteinTest {

    private String base = "Nicole Walker";
    private String[] targets = {
        "Jon Henderson",
                "Tina Henderson",
                "Nicole Waker",
                "Robin Orwoll",
                "Greg Walker",
                "Robin Orwoll",
                "Greg Orwoll",
    };


    @Test
    public void testDistance() {

        for(String target : targets) {
            System.out.println(Levenshtein.distance(base, target));
        }
    }

    @Test
    public void testDistanceRate() {

        for(String target : targets) {
            System.out.println(Levenshtein.rateOfStringMatch(base, target));
        }
    }

}
