package com.wonders.hms.util;

public class Levenshtein {

    public static int distance(String baseStr, String targetStr) {
        baseStr = baseStr.toLowerCase();
        targetStr = targetStr.toLowerCase();

        int [] costs = new int [targetStr.length() + 1];

        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }

        for (int i = 1; i <= baseStr.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= targetStr.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), baseStr.charAt(i - 1) == targetStr.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }

        return costs[targetStr.length()];
    }

    public static double rateOfStringMatch(String baseStr, String targetStr) {
        int maxLength = Math.max(baseStr.length(), targetStr.length());

        int result = maxLength - Levenshtein.distance(baseStr, targetStr);

        double rate = result / (double) maxLength;

        return rate;
    }
}
