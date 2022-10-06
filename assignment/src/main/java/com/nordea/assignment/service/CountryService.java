package com.nordea.assignment.service;

import com.nordea.assignment.dto.CountryListResponse;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private String countriesNowUrl;
    private RestTemplate restTemplate;

    @Autowired
    public CountryService(@Value("${remote.service.url}") String countriesNowUrl, RestTemplate restTemplate) {
        this.countriesNowUrl = countriesNowUrl;
        this.restTemplate = restTemplate;
    }

    public List<CountryListItem> retrieveCountryList() {
        ResponseEntity<CountryListResponse> response
                = restTemplate.getForEntity(countriesNowUrl, CountryListResponse.class);
        CountryListResponse countryList = response.getBody();
        return Arrays.stream(countryList.getData())
                .map((item) -> {
                    CountryListItem modelItem = new CountryListItem(item.getCountry(), item.getIso2());
                    return modelItem;
                })
                .collect(Collectors.toList());
    }

    public Country retrieveCountry(String countryName) {
        Country country = new Country();
        country.setName(countryName);
        return country;
    }
}
