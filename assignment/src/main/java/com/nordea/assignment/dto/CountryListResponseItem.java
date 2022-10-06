package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryListResponseItem {
    private String iso2;
    private String iso3;
    private String country;
    private String[] cities;
}
