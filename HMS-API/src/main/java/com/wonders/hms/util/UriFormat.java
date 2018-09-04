package com.wonders.hms.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;

public class UriFormat {
    private StringBuilder builder = new StringBuilder();

    @JsonAnySetter
    public void addToUri(String name, Object property) {

        if (builder.length() > 0) {
            builder.append("&");
        }

        builder.append(name).append("=");

        if (property != null ) {
            if (property instanceof ArrayList) {
                if (((ArrayList) property).size() != 0) {
                    builder.append(convertToString(",", (ArrayList) property));
                }
            }
            else {
                builder.append(property);
            }
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    static String convertToString(String delimiter, ArrayList arrayList) {
        StringBuilder builder = new StringBuilder();
        // Append all Integers in StringBuilder to the StringBuilder.
        for (Object item : arrayList) {
            builder.append(item);
            builder.append(delimiter);
        }
        // Remove last delimiter with setLength.
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}
