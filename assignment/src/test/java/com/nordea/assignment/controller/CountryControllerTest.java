package com.nordea.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordea.assignment.model.Countries;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import com.nordea.assignment.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    final String COUNTRY_1_NAME = "Country-1";
    final String COUNTRY_1_CODE = "C1";
    final String COUNTRY_1_CAPITAL = "Capital-1";
    final int COUNTRY_1_POPULATION = 100000;
    final String COUNTRY_1_FLAG = "Flag-1";
    final String COUNTRY_2_NAME = "Country-2";
    final String COUNTRY_2_CODE = "C2";
    final String COUNTRY_2_CAPITAL = "Capital-2";
    final int COUNTRY_2_POPULATION = 200000;
    final String COUNTRY_2_FLAG = "Flag-2";
    final String COUNTRY_NA = "Country-NA";
    final String COUNTRY_NA_MSG = "Country not found!";

    private Country country1;
    private Country country2;
    private CountryListItem countryListItem1;
    private CountryListItem countryListItem2;
    private List<CountryListItem> countriesList;
    private Countries countries;

    @BeforeEach
    public void setUp() {
        countriesList = new ArrayList<>();

        country1 = new Country(COUNTRY_1_NAME, COUNTRY_1_CODE, COUNTRY_1_CAPITAL, COUNTRY_1_POPULATION, COUNTRY_1_FLAG);
        countryListItem1 = new CountryListItem(COUNTRY_1_NAME, COUNTRY_1_CODE);
        countriesList.add(countryListItem1);

        country2 = new Country(COUNTRY_2_NAME, COUNTRY_2_CODE, COUNTRY_2_CAPITAL, COUNTRY_2_POPULATION, COUNTRY_2_FLAG);
        countryListItem2 = new CountryListItem(COUNTRY_2_NAME, COUNTRY_2_CODE);
        countriesList.add(countryListItem2);

        countries = new Countries(countriesList);
    }

    @Test
    public void retrieveCountryListSuccess() throws Exception {
        Mockito.when(countryService.retrieveCountryList()).thenReturn(countries);

        mockMvc.perform(get("/v1/countries")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countries.[0].name").value(COUNTRY_1_NAME))
                .andExpect(jsonPath("$.countries.[1].name").value(COUNTRY_2_NAME));

        verify(countryService, times(1)).retrieveCountryList();
    }

    @Test
    public void retrieveCountryByNameSuccess() throws Exception {
        Mockito.when(countryService.retrieveCountry(COUNTRY_1_NAME)).thenReturn(country1);
        Mockito.when(countryService.retrieveCountry(COUNTRY_2_NAME)).thenReturn(country2);

        mockMvc.perform(get("/v1/countries/" + COUNTRY_1_NAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COUNTRY_1_NAME))
                .andExpect(jsonPath("$.country_code").value(COUNTRY_1_CODE));

        mockMvc.perform(get("/v1/countries/" + COUNTRY_2_NAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COUNTRY_2_NAME))
                .andExpect(jsonPath("$.country_code").value(COUNTRY_2_CODE));

        verify(countryService, times(1)).retrieveCountry(COUNTRY_1_NAME);
        verify(countryService, times(1)).retrieveCountry(COUNTRY_2_NAME);
    }

    @Test
    public void retrieveCountryByNameExceptions() throws Exception {
        Mockito.when(countryService.retrieveCountry(COUNTRY_NA))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/v1/countries/" + COUNTRY_NA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value(COUNTRY_NA_MSG));

        verify(countryService, times(1)).retrieveCountry(COUNTRY_NA);
    }
}
