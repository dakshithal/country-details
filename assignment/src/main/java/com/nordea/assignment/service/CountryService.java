package com.nordea.assignment.service;

import com.nordea.assignment.model.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    public List<Country> retrieveCountryList() {
        return new ArrayList<>();
    }

    public Country retrieveCountry(String countryName) {
        Country country = new Country();
        country.setName(countryName);
        return country;
    }
}
