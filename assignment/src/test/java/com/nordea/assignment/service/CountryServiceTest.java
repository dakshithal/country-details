package com.nordea.assignment.service;

import com.nordea.assignment.client.RemoteServiceClient;
import com.nordea.assignment.dto.*;
import com.nordea.assignment.model.Countries;
import com.nordea.assignment.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CountryServiceTest {
    @Mock
    private RemoteServiceClient remoteClient;
    private CountryService countryService;

    final String TEST_URL = "https://countries-remote-url.dummy";
    final String CAPITAL_QUERY = "/capital/";
    final String POPULATION_QUERY = "/population/";
    final String FLAG_QUERY = "/flag/";

    final String COUNTRY_1_NAME = "Country-1";
    final String COUNTRY_1_CODE = "C1";
    final String COUNTRY_1_CAPITAL = "Capital-1";
    final String COUNTRY_1_FLAG = "Flag-1";
    final int COUNTRY_1_POPULATION_1 = 100000;
    final int COUNTRY_1_POPULATION_1_YEAR = 2010;
    final int COUNTRY_1_POPULATION_2 = 100001;
    final int COUNTRY_1_POPULATION_2_YEAR = 2012;

    final String COUNTRY_2_NAME = "Country-2";
    final String COUNTRY_2_CODE = "C2";
    final String COUNTRY_2_CAPITAL = "Capital-2";
    final String COUNTRY_2_FLAG = "Flag-2";
    final int COUNTRY_2_POPULATION_1 = 200000;
    final int COUNTRY_2_POPULATION_1_YEAR = 2012;
    final int COUNTRY_2_POPULATION_2 = 200001;
    final int COUNTRY_2_POPULATION_2_YEAR = 2010;

    final String COUNTRY_NA = "Country-NA";
    final String COUNTRY_NA_MSG = "404 NOT_FOUND";

    private CountryListResponse countryListResponse;
    private CountryListResponseItem countryListResponseItem1;
    private CountryListResponseItem countryListResponseItem2;

    private CountryCapitalResponse country1CapitalResponse;
    private CountryCapitalData country1CapitalData;
    private CountryPopulationResponse country1PopulationResponse;
    private CountryPopulationData country1PopulationData;
    private AnnualPopulation country1AnnualPopulation1;
    private AnnualPopulation country1AnnualPopulation2;
    private CountryFlagResponse country1FlagResponse;
    private CountryFlagData country1FlagData;

    private CountryCapitalResponse country2CapitalResponse;
    private CountryCapitalData country2CapitalData;
    private CountryPopulationResponse country2PopulationResponse;
    private CountryPopulationData country2PopulationData;
    private AnnualPopulation country2AnnualPopulation1;
    private AnnualPopulation country2AnnualPopulation2;
    private CountryFlagResponse country2FlagResponse;
    private CountryFlagData country2FlagData;

    private CountryCapitalResponse countryNaCapitalResponse;
    private CountryPopulationResponse countryNaPopulationResponse;
    private CountryFlagResponse countryNaFlagResponse;

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
        Mockito.when(remoteClient.getForObject(TEST_URL, CountryListResponse.class))
                .thenReturn(countryListResponse);

        country1CapitalResponse = new CountryCapitalResponse();
        country1CapitalData = new CountryCapitalData();
        country1CapitalData.setName(COUNTRY_1_NAME);
        country1CapitalData.setIso2(COUNTRY_1_CODE);
        country1CapitalData.setCapital(COUNTRY_1_CAPITAL);
        country1CapitalResponse.setData(country1CapitalData);
        Mockito.when(remoteClient.getForObject(TEST_URL + CAPITAL_QUERY + COUNTRY_1_NAME, CountryCapitalResponse.class))
                .thenReturn(country1CapitalResponse);

        country1FlagResponse = new CountryFlagResponse();
        country1FlagData = new CountryFlagData();
        country1FlagData.setFlag(COUNTRY_1_FLAG);
        country1FlagResponse.setData(country1FlagData);
        Mockito.when(remoteClient.getForObject(TEST_URL + FLAG_QUERY + COUNTRY_1_NAME, CountryFlagResponse.class))
                .thenReturn(country1FlagResponse);

        country1PopulationResponse = new CountryPopulationResponse();
        country1PopulationData = new CountryPopulationData();
        country1AnnualPopulation1 = new AnnualPopulation();
        country1AnnualPopulation1.setYear(COUNTRY_1_POPULATION_1_YEAR);
        country1AnnualPopulation1.setValue(COUNTRY_1_POPULATION_1);
        country1AnnualPopulation2 = new AnnualPopulation();
        country1AnnualPopulation2.setYear(COUNTRY_1_POPULATION_2_YEAR);
        country1AnnualPopulation2.setValue(COUNTRY_1_POPULATION_2);
        AnnualPopulation[] country1Populations = { country1AnnualPopulation1, country1AnnualPopulation2 };
        country1PopulationData.setPopulationCounts(country1Populations);
        country1PopulationResponse.setData(country1PopulationData);
        Mockito.when(remoteClient.getForObject(TEST_URL + POPULATION_QUERY + COUNTRY_1_NAME, CountryPopulationResponse.class))
                .thenReturn(country1PopulationResponse);

        country2CapitalResponse = new CountryCapitalResponse();
        country2CapitalData = new CountryCapitalData();
        country2CapitalData.setName(COUNTRY_2_NAME);
        country2CapitalData.setIso2(COUNTRY_2_CODE);
        country2CapitalData.setCapital(COUNTRY_2_CAPITAL);
        country2CapitalResponse.setData(country2CapitalData);
        Mockito.when(remoteClient.getForObject(TEST_URL + CAPITAL_QUERY + COUNTRY_2_NAME, CountryCapitalResponse.class))
                .thenReturn(country2CapitalResponse);

        country2FlagResponse = new CountryFlagResponse();
        country2FlagData = new CountryFlagData();
        country2FlagData.setFlag(COUNTRY_2_FLAG);
        country2FlagResponse.setData(country2FlagData);
        Mockito.when(remoteClient.getForObject(TEST_URL + FLAG_QUERY + COUNTRY_2_NAME, CountryFlagResponse.class))
                .thenReturn(country2FlagResponse);

        country2PopulationResponse = new CountryPopulationResponse();
        country2PopulationData = new CountryPopulationData();
        country2AnnualPopulation1 = new AnnualPopulation();
        country2AnnualPopulation1.setYear(COUNTRY_2_POPULATION_1_YEAR);
        country2AnnualPopulation1.setValue(COUNTRY_2_POPULATION_1);
        country2AnnualPopulation2 = new AnnualPopulation();
        country2AnnualPopulation2.setYear(COUNTRY_2_POPULATION_2_YEAR);
        country2AnnualPopulation2.setValue(COUNTRY_2_POPULATION_2);
        AnnualPopulation[] country2Populations = { country2AnnualPopulation1, country2AnnualPopulation2 };
        country2PopulationData.setPopulationCounts(country2Populations);
        country2PopulationResponse.setData(country2PopulationData);
        Mockito.when(remoteClient.getForObject(TEST_URL + POPULATION_QUERY + COUNTRY_2_NAME, CountryPopulationResponse.class))
                .thenReturn(country2PopulationResponse);

        countryNaCapitalResponse = new CountryCapitalResponse();
        countryNaCapitalResponse.setError(true);
        countryNaCapitalResponse.setMsg(COUNTRY_NA_MSG);
        Mockito.when(remoteClient.getForObject(TEST_URL + CAPITAL_QUERY + COUNTRY_NA, CountryCapitalResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        countryNaFlagResponse = new CountryFlagResponse();
        countryNaFlagResponse.setError(true);
        Mockito.when(remoteClient.getForObject(TEST_URL + FLAG_QUERY + COUNTRY_NA, CountryFlagResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        countryNaPopulationResponse = new CountryPopulationResponse();
        countryNaPopulationResponse.setError(true);
        Mockito.when(remoteClient.getForObject(TEST_URL + POPULATION_QUERY + COUNTRY_NA, CountryPopulationResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        countryService = new CountryService(
                TEST_URL, CAPITAL_QUERY, POPULATION_QUERY, FLAG_QUERY, remoteClient);
    }

    @Test
    public void retrieveCountryListSuccess() {
        Countries countries = countryService.retrieveCountryList();
        assertNotNull(countries);
        assertEquals(2, countries.getCountries().size());
        assertEquals(COUNTRY_1_NAME, countries.getCountries().get(0).getName());
        assertEquals(COUNTRY_2_NAME, countries.getCountries().get(1).getName());
    }

    @Test
    public void retrieveCountryByNameSuccess() {
        Country country1 = countryService.retrieveCountry(COUNTRY_1_NAME);
        assertNotNull(country1);
        assertEquals(COUNTRY_1_NAME, country1.getName());
        assertEquals(COUNTRY_1_CODE, country1.getCountryCode());
        assertEquals(COUNTRY_1_CAPITAL, country1.getCapital());
        assertEquals(COUNTRY_1_FLAG, country1.getFlagFileUrl());
        assertEquals(COUNTRY_1_POPULATION_2, country1.getPopulation());

        Country country2 = countryService.retrieveCountry(COUNTRY_2_NAME);
        assertNotNull(country2);
        assertEquals(COUNTRY_2_NAME, country2.getName());
        assertEquals(COUNTRY_2_CODE, country2.getCountryCode());
        assertEquals(COUNTRY_2_CAPITAL, country2.getCapital());
        assertEquals(COUNTRY_2_FLAG, country2.getFlagFileUrl());
        assertEquals(COUNTRY_2_POPULATION_1, country2.getPopulation());
    }

    @Test
    public void retrieveCountryByNameNotAvailable() {
        Exception exception = assertThrows(Exception.class, () -> countryService.retrieveCountry(COUNTRY_NA));
        assertEquals(COUNTRY_NA_MSG, exception.getMessage());
    }
}
