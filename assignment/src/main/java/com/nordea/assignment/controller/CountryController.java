package com.nordea.assignment.controller;

import com.nordea.assignment.model.Countries;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public Countries retrieveCountryList() {
        return countryService.retrieveCountryList();
    }

    @GetMapping("/{name}")
    public Country retrieveCountry(@PathVariable(value = "name") String countryName) {
        return countryService.retrieveCountry(countryName);
    }
}
