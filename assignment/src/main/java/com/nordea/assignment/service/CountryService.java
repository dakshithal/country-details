package com.nordea.assignment.service;

import com.nordea.assignment.dto.*;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private String externalCountryServiceUrl;
    private String capitalQuery;
    private String populationQuery;
    private String flagQuery;
    private RestTemplate restTemplate;

    @Autowired
    public CountryService(
            @Value("${remote.service.url}") String externalCountryServiceUrl,
            @Value("${remote.service.query.capital}") String capitalQuery,
            @Value("${remote.service.query.population}") String populationQuery,
            @Value("${remote.service.query.flag}") String flagQuery,
            RestTemplate restTemplate) {
        this.externalCountryServiceUrl = externalCountryServiceUrl;
        this.capitalQuery = capitalQuery;
        this.populationQuery = populationQuery;
        this.flagQuery = flagQuery;
        this.restTemplate = restTemplate;
    }

    public List<CountryListItem> retrieveCountryList() {
        CountryListResponse response
                = restTemplate.getForObject(externalCountryServiceUrl, CountryListResponse.class);
        return Arrays.stream(response.getData())
                .map((item) -> {
                    CountryListItem modelItem = new CountryListItem(item.getCountry(), item.getIso2());
                    return modelItem;
                })
                .collect(Collectors.toList());
    }

    public Country retrieveCountry(String countryName) {
        CountryCapitalResponse capitalResponse = restTemplate.getForObject(
                externalCountryServiceUrl + capitalQuery + countryName, CountryCapitalResponse.class);
        CountryPopulationResponse populationResponse= restTemplate.getForObject(
                externalCountryServiceUrl + populationQuery + countryName, CountryPopulationResponse.class);
        CountryFlagResponse flagResponse = restTemplate.getForObject(
                externalCountryServiceUrl + flagQuery + countryName, CountryFlagResponse.class);

        String name = capitalResponse.getData().getName();
        String capital = capitalResponse.getData().getCapital();
        String code = capitalResponse.getData().getIso2();
        int population = Arrays.stream(populationResponse.getData().getPopulationCounts())
                .sorted(Comparator.comparingInt(AnnualPopulation::getYear).reversed())
                .findFirst()
                .get().getValue();
        String flagUrl = flagResponse.getData().getFlag();

        return new Country(name, code, capital, population, flagUrl);
    }
}
