package com.oponiti.shop.common.domain.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int statusCode;
    private String message;
}
