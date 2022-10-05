package com.nordea.assignment.controller;

import com.nordea.assignment.model.Country;
import com.nordea.assignment.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "countries")
public class CountryController {

    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> retrieveCountryList() {
        return countryService.retrieveCountryList();
    }

    @GetMapping("/{name}")
    public Country retrieveCountry(@PathVariable(value = "name") String countryName) {
        return countryService.retrieveCountry(countryName);
    }
}
