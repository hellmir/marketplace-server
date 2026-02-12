package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoOutOrdGoodsByInvoiceResponse(
        String invoiceNo,
        List<FasstoOutOrdGoodsDeliveredResponse> goodsDeliveredList
) {
}
