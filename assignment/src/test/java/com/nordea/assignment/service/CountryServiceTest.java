package com.nordea.assignment.service;

import com.nordea.assignment.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class CountryServiceTest {
    CountryService countryService;

    final String COUNTRY_1_NAME = "Country-1";

    @BeforeEach
    public void setUp() {
        countryService = new CountryService();
    }

    @Test
    public void retrieveCountryListSuccess() {
        List<Country> countries = countryService.retrieveCountryList();
        assertNotNull(countries);
    }

    @Test
    public void retrieveCountryByNameSuccess() {
        Country country = countryService.retrieveCountry(COUNTRY_1_NAME);
        assertNotNull(country);
        assertEquals(COUNTRY_1_NAME, country.getName());
    }
}
