package com.nordea.assignment.service;

import com.nordea.assignment.dto.*;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    final String CAPITAL_QUERY = "/capital/";
    final String POPULATION_QUERY = "/population/";
    final String FLAG_QUERY = "/flag/";
    final String COUNTRY_1_NAME = "Country-1";
    final String COUNTRY_1_CODE = "C1";
    final String COUNTRY_1_CAPITAL = "Capital-1";
    final int COUNTRY_1_POPULATION_1 = 100000;
    final int COUNTRY_1_POPULATION_1_YEAR = 2010;
    final int COUNTRY_1_POPULATION_2 = 100001;
    final int COUNTRY_1_POPULATION_2_YEAR = 2012;
    final String COUNTRY_1_FLAG = "Flag-1";
    final String COUNTRY_2_NAME = "Country-2";
    final String COUNTRY_2_CODE = "C2";
    final String COUNTRY_2_CAPITAL = "Capital-2";
    final int COUNTRY_2_POPULATION_1 = 200000;
    final int COUNTRY_2_POPULATION_1_YEAR = 2012;
    final int COUNTRY_2_POPULATION_2 = 200001;
    final int COUNTRY_2_POPULATION_2_YEAR = 2010;
    final String COUNTRY_2_FLAG = "Flag-2";

    private CountryListResponse countryListResponse;
    private CountryListResponseItem countryListResponseItem1;
    private CountryListResponseItem countryListResponseItem2;

    private CountryCapitalResponse capitalResponse;
    private CountryCapitalData capitalData;
    private CountryPopulationResponse populationResponse;
    private CountryPopulationData populationData;
    private AnnualPopulation annualPopulation1;
    private AnnualPopulation annualPopulation2;
    private CountryFlagResponse flagResponse;
    private CountryFlagData flagData;

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
        Mockito.when(restTemplate.getForObject(TEST_URL, CountryListResponse.class))
                .thenReturn(countryListResponse);

        capitalResponse = new CountryCapitalResponse();
        capitalData = new CountryCapitalData();
        capitalData.setName(COUNTRY_1_NAME);
        capitalData.setIso2(COUNTRY_1_CODE);
        capitalData.setCapital(COUNTRY_1_CAPITAL);
        capitalResponse.setData(capitalData);
        Mockito.when(restTemplate.getForObject(TEST_URL + CAPITAL_QUERY + COUNTRY_1_NAME, CountryCapitalResponse.class))
                .thenReturn(capitalResponse);

        populationResponse = new CountryPopulationResponse();
        populationData = new CountryPopulationData();
        annualPopulation1 = new AnnualPopulation();
        annualPopulation1.setYear(COUNTRY_1_POPULATION_1_YEAR);
        annualPopulation1.setValue(COUNTRY_1_POPULATION_1);
        annualPopulation2 = new AnnualPopulation();
        annualPopulation2.setYear(COUNTRY_1_POPULATION_2_YEAR);
        annualPopulation2.setValue(COUNTRY_1_POPULATION_2);
        AnnualPopulation[] annualPopulations = { annualPopulation1, annualPopulation2 };
        populationData.setPopulationCounts(annualPopulations);
        populationResponse.setData(populationData);
        Mockito.when(restTemplate.getForObject(TEST_URL + POPULATION_QUERY + COUNTRY_1_NAME, CountryPopulationResponse.class))
                .thenReturn(populationResponse);

        flagResponse = new CountryFlagResponse();
        flagData = new CountryFlagData();
        flagData.setFlag(COUNTRY_1_FLAG);
        flagResponse.setData(flagData);
        Mockito.when(restTemplate.getForObject(TEST_URL + FLAG_QUERY + COUNTRY_1_NAME, CountryFlagResponse.class))
                .thenReturn(flagResponse);

        countryService = new CountryService(TEST_URL, CAPITAL_QUERY, POPULATION_QUERY, FLAG_QUERY, restTemplate);
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
        assertEquals(COUNTRY_1_CODE, country.getCountryCode());
        assertEquals(COUNTRY_1_CAPITAL, country.getCapital());
        assertEquals(COUNTRY_1_POPULATION_2, country.getPopulation());
        assertEquals(COUNTRY_1_FLAG, country.getFlagFileUrl());
    }
}
