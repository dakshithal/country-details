package com.nordea.assignment.service;

import com.nordea.assignment.client.RemoteServiceClient;
import com.nordea.assignment.dto.*;
import com.nordea.assignment.model.Countries;
import com.nordea.assignment.model.Country;
import com.nordea.assignment.model.CountryListItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("Started retrieving country list");
        CountryListResponse response = remoteClient.getForObject(externalUrl, CountryListResponse.class);
        log.debug("Completed retrieving country list");

        log.debug("Started creating response country list");
        List<CountryListItem> countries = Arrays.stream(response.getData())
                .map((item) -> {
                    CountryListItem modelItem = new CountryListItem(item.getCountry(), item.getIso2());
                    return modelItem;
                })
                .collect(Collectors.toList());
        log.debug("Completed creating response country list");
        return new Countries(countries);
    }

    public Country retrieveCountry(String countryName) {
        log.debug("Started retrieving capital for country: " + countryName);
        CountryCapitalResponse capitalResponse =
                remoteClient.getForObject(externalUrl + capitalQuery + countryName, CountryCapitalResponse.class);
        log.debug("Completed retrieving capital for country: " + countryName);

        log.debug("Started retrieving flag for country: " + countryName);
        CountryFlagResponse flagResponse = null;
        try {
            flagResponse = remoteClient.getForObject(externalUrl + flagQuery + countryName, CountryFlagResponse.class);
            log.debug("Completed retrieving flag for country: " + countryName);
        } catch (HttpClientErrorException e) {
            log.error("Flag data is unavailable for country: " + countryName);
        }

        log.debug("Started retrieving population for country: " + countryName);
        CountryPopulationResponse populationResponse = null;
        try {
            populationResponse =
                    remoteClient.getForObject(externalUrl + populationQuery + countryName, CountryPopulationResponse.class);
            log.debug("Completed retrieving population for country: " + countryName);
        } catch (HttpClientErrorException e) {
            log.error("Population data is unavailable for country: " + countryName);
        }

        String name = capitalResponse.getData().getName();
        String capital = capitalResponse.getData().getCapital();
        String code = capitalResponse.getData().getIso2();
        String flagUrl = flagResponse == null ? "" : flagResponse.getData().getFlag();
        if (populationResponse == null) {
            Country country = new Country();
            country.setName(name);
            country.setCountryCode(code);
            country.setCapital(capital);
            country.setFlagFileUrl(flagUrl);
            return country;
        } else {
            int population = Arrays.stream(populationResponse.getData().getPopulationCounts())
                    .sorted(Comparator.comparingInt(AnnualPopulation::getYear).reversed())
                    .findFirst()
                    .get().getValue();
            return new Country(name, code, capital, flagUrl, population);
        }
    }
}
