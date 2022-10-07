package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryFlagResponse extends BaseResponse {
    private CountryFlagData data;
}
