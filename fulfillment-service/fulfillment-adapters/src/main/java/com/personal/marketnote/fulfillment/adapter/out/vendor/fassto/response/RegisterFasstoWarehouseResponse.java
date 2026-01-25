package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterFasstoWarehouseResponse(
        FasstoResponseHeader header,
        RegisterFasstoWarehouseDataResponse data,
        Object errorInfo
) {
    public boolean isSuccess() {
        return header != null && header.isSuccess() && data != null && data.isSuccess();
    }
}
