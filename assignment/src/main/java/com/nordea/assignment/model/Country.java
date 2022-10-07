package com.nordea.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {
    private String name;
    @JsonProperty(value = "country_code")
    private String countryCode;
    private String capital;
    private int population;
    @JsonProperty(value = "flag_file_url")
    private String flagFileUrl;
}
