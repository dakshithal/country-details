package com.nordea.assignment.model;

import lombok.Data;

@Data
public class Country {
    private String name;
    private String countryCode;
    private String capital;
    private int population;
    private String flagFileUrl;
}
