package com.nordea.assignment.service;

import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    public List<CountryListItem> retrieveCountryList() {
        return new ArrayList<>();
    }

    public Country retrieveCountry(String countryName) {
        Country country = new Country();
        country.setName(countryName);
        return country;
    }
}
