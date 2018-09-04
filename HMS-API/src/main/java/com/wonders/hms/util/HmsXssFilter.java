package com.wonders.hms.util;

import com.wonders.hms.user.vo.SearchHistory;

public class HmsXssFilter {

    public static SearchHistory filter(SearchHistory searchHistory) {
        searchHistory.setSearchedPlace(replaceFilterString(searchHistory.getSearchedPlace()));
        searchHistory.setCheckin(replaceFilterString(searchHistory.getCheckin()));
        searchHistory.setCheckout(replaceFilterString(searchHistory.getCheckout()));
        searchHistory.setChildrenAge(replaceFilterString(searchHistory.getChildrenAge()));
        searchHistory.setResultUrl(replaceFilterString(searchHistory.getResultUrl()));
        searchHistory.setReservationVendorUrl(replaceFilterString(searchHistory.getReservationVendorUrl()));
        searchHistory.setRealHotelName(replaceFilterString(searchHistory.getRealHotelName()));
        searchHistory.setRealCityName(replaceFilterString(searchHistory.getRealHotelName()));
        searchHistory.setRealCheckin(replaceFilterString(searchHistory.getRealCheckin()));
        searchHistory.setRealCheckOut(replaceFilterString(searchHistory.getRealCheckOut()));
        searchHistory.setRealHotelAddress(replaceFilterString(searchHistory.getRealHotelAddress()));
        searchHistory.setRealHotelPhon(replaceFilterString(searchHistory.getRealHotelPhon()));

        return searchHistory;
    }

    private static String replaceFilterString(String str) {
        str = str.replace("&", "&amp;");

        str = str.replace("<", "&lt;");
        str = str.replace(">", "&gt;");
        str = str.replace("(", "&lpar;");
        str = str.replace(")", "&rpar;");
        str = str.replace("#", "&num;");
        str = str.replace("\'", "&apos;");
        str = str.replace("\"", "&quot;");

        return str;
    }
}
