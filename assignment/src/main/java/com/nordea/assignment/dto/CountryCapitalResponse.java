package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryCapitalResponse extends BaseResponse {
    private CountryCapitalData data;
}
