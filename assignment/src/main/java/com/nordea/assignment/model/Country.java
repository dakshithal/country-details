package com.nordea.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {
    private String name;
    private String countryCode;
    private String capital;
    private int population;
    private String flagFileUrl;
}
