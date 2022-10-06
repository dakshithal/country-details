package com.nordea.assignment.dto;

import lombok.Data;

@Data
public class CountryListResponse extends BaseResponse {
    private CountryListResponseItem[] data;
}
