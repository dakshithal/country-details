package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryPopulationData {
    private String country;
    private String code;
    private String iso3;
    private AnnualPopulation[] populationCounts;
}
