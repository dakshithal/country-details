package com.nordea.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Countries {
    private List<CountryListItem> countries;
}
