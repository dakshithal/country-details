package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryPopulationResponse extends BaseResponse {
    private CountryPopulationData data;
}
