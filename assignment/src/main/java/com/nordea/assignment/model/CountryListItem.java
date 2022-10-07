package com.nordea.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryListItem {
    private String name;
    @JsonProperty(value = "country_code")
    private String countryCode;
}
