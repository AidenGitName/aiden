package com.wonders.hms.place.persistence;

import com.wonders.hms.place.domain.Country;

import java.util.List;

public interface CountryMapper {
    List<Country> getCountries();
}
