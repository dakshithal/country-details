package com.nordea.assignment.dto;

import lombok.Data;

@Data
public abstract class BaseResponse {
    private boolean error;
    private String msg;
}
