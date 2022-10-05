package com.nordea.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordea.assignment.model.Country;
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
    ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    final String COUNTRY_1_NAME = "Country-1";
    final String COUNTRY_1_CODE = "C1";
    final String COUNTRY_2_NAME = "Country-2";
    final String COUNTRY_2_CODE = "C2";

    private Country country1;
    private Country country2;
    private List<Country> countries;

    @BeforeEach
    public void setUp() {
        countries = new ArrayList<>();

        country1 = new Country();
        country1.setName(COUNTRY_1_NAME);
        country1.setCountryCode(COUNTRY_1_CODE);
        countries.add(country1);

        country2 = new Country();
        country2.setName(COUNTRY_2_NAME);
        country2.setCountryCode(COUNTRY_2_CODE);
        countries.add(country2);
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

        mockMvc.perform(get("/countries/" + COUNTRY_1_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COUNTRY_1_NAME))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_1_CODE));

        verify(countryService, times(1)).retrieveCountry(COUNTRY_1_NAME);
    }
}
