package com.nordea.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryListItem {
    private String name;
    private String countryCode;
}
