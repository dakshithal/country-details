package com.nordea.assignment.service;

import com.nordea.assignment.dto.CountryListResponse;
import com.nordea.assignment.dto.CountryListResponseItem;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class CountryServiceTest {
    @Mock
    private RestTemplate restTemplate;
    private CountryService countryService;

    final String TEST_URL = "https://countries-remote-url.dummy";
    final String COUNTRY_1_NAME = "Country-1";
    final String COUNTRY_1_CODE = "C1";
    final String COUNTRY_2_NAME = "Country-2";
    final String COUNTRY_2_CODE = "C2";

    private CountryListResponse countryListResponse;
    private CountryListResponseItem countryListResponseItem1;
    private CountryListResponseItem countryListResponseItem2;

    @BeforeEach
    public void setUp() {
        countryListResponse = new CountryListResponse();
        countryListResponse.setError(false);
        countryListResponse.setMsg("Countries and codes successful");
        countryListResponseItem1 = new CountryListResponseItem();
        countryListResponseItem1.setCountry(COUNTRY_1_NAME);
        countryListResponseItem1.setIso2(COUNTRY_1_CODE);
        countryListResponseItem2 = new CountryListResponseItem();
        countryListResponseItem2.setCountry(COUNTRY_2_NAME);
        countryListResponseItem2.setIso2(COUNTRY_2_CODE);
        CountryListResponseItem[] items = { countryListResponseItem1, countryListResponseItem2 };
        countryListResponse.setData(items);

        Mockito.when(restTemplate.getForEntity(TEST_URL, CountryListResponse.class))
                .thenReturn(new ResponseEntity<>(countryListResponse, HttpStatus.OK));
        countryService = new CountryService(TEST_URL, restTemplate);
    }

    @Test
    public void retrieveCountryListSuccess() {
        List<CountryListItem> countries = countryService.retrieveCountryList();
        assertNotNull(countries);
        assertEquals(2, countries.size());
        assertEquals(COUNTRY_1_NAME, countries.get(0).getName());
        assertEquals(COUNTRY_2_NAME, countries.get(1).getName());
    }

    @Test
    public void retrieveCountryByNameSuccess() {
        Country country = countryService.retrieveCountry(COUNTRY_1_NAME);
        assertNotNull(country);
        assertEquals(COUNTRY_1_NAME, country.getName());
    }
}
