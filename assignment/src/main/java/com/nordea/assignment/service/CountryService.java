package com.nordea.assignment.service;

import com.nordea.assignment.client.RemoteServiceClient;
import com.nordea.assignment.dto.*;
import com.nordea.assignment.model.Countries;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final String externalUrl;
    private final String capitalQuery;
    private final String populationQuery;
    private final String flagQuery;
    private final RemoteServiceClient remoteClient;

    @Autowired
    public CountryService(
            @Value("${remote.service.url}") String externalCountryServiceUrl,
            @Value("${remote.service.query.capital}") String capitalQuery,
            @Value("${remote.service.query.population}") String populationQuery,
            @Value("${remote.service.query.flag}") String flagQuery,
            RemoteServiceClient remoteClient) {
        this.externalUrl = externalCountryServiceUrl;
        this.capitalQuery = capitalQuery;
        this.populationQuery = populationQuery;
        this.flagQuery = flagQuery;
        this.remoteClient = remoteClient;
    }

    public Countries retrieveCountryList() {
        CountryListResponse response = remoteClient.getForObject(externalUrl, CountryListResponse.class);
        List<CountryListItem> countries = Arrays.stream(response.getData())
                .map((item) -> {
                    CountryListItem modelItem = new CountryListItem(item.getCountry(), item.getIso2());
                    return modelItem;
                })
                .collect(Collectors.toList());
        return new Countries(countries);
    }

    public Country retrieveCountry(String countryName) {
        CountryCapitalResponse capitalResponse =
                remoteClient.getForObject(externalUrl + capitalQuery + countryName, CountryCapitalResponse.class);
        CountryPopulationResponse populationResponse =
                remoteClient.getForObject(externalUrl + populationQuery + countryName, CountryPopulationResponse.class);
        CountryFlagResponse flagResponse =
                remoteClient.getForObject(externalUrl + flagQuery + countryName, CountryFlagResponse.class);
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
