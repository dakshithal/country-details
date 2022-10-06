package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryListResponse {
    private boolean error;
    private String msg;
    private CountryListResponseItem[] data;
}
