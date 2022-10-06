package com.nordea.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Country country1;
    private Country country2;
    private CountryListItem countryListItem1;
    private CountryListItem countryListItem2;
    private List<CountryListItem> countries;

    @BeforeEach
    public void setUp() {
        countries = new ArrayList<>();

        country1 = new Country(COUNTRY_1_NAME, COUNTRY_1_CODE, COUNTRY_1_CAPITAL, COUNTRY_1_POPULATION, COUNTRY_1_FLAG);
        countryListItem1 = new CountryListItem(COUNTRY_1_NAME, COUNTRY_1_CODE);
        countries.add(countryListItem1);

        country2 = new Country(COUNTRY_2_NAME, COUNTRY_2_CODE, COUNTRY_2_CAPITAL, COUNTRY_2_POPULATION, COUNTRY_2_FLAG);
        countryListItem2 = new CountryListItem(COUNTRY_2_NAME, COUNTRY_2_CODE);
        countries.add(countryListItem2);
    }

    @Test
    public void retrieveCountryListSuccess() throws Exception {
        Mockito.when(countryService.retrieveCountryList()).thenReturn(countries);

        mockMvc.perform(get("/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countries))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(COUNTRY_1_NAME))
                .andExpect(jsonPath("$.[1].name").value(COUNTRY_2_NAME));

        verify(countryService, times(1)).retrieveCountryList();
    }

    @Test
    public void retrieveCountryByNameSuccess() throws Exception {
        Mockito.when(countryService.retrieveCountry(COUNTRY_1_NAME)).thenReturn(country1);
        Mockito.when(countryService.retrieveCountry(COUNTRY_2_NAME)).thenReturn(country2);

        mockMvc.perform(get("/countries/" + COUNTRY_1_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COUNTRY_1_NAME))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_1_CODE));

        mockMvc.perform(get("/countries/" + COUNTRY_2_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COUNTRY_2_NAME))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_2_CODE));

        verify(countryService, times(1)).retrieveCountry(COUNTRY_1_NAME);
        verify(countryService, times(1)).retrieveCountry(COUNTRY_2_NAME);
    }
}
