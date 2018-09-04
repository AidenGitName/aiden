package com.wonders.hms.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Jaccard {

    public static double similarity(String compare1, String compare2) {
        String[] compare1Arr = compare1.split(",|\\s");
        String[] compare2Arr = compare2.split(",|\\s");

        Set<String> retainSet = new HashSet<>(Arrays.asList(compare1Arr));
        retainSet.retainAll(new HashSet<>(Arrays.asList(compare2Arr)));

        Set<String> addAllSet = new HashSet<>(Arrays.asList(compare1Arr));
        addAllSet.addAll(new HashSet<>(Arrays.asList(compare2Arr)));

        double similarityRate = retainSet.size() / (double) addAllSet.size();

        return similarityRate;
    }
}
