package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.ProductSortPropertyItemResult;

public record ProductSortPropertyItemResponse(
        String name,
        String description
) {

    public static ProductSortPropertyItemResponse from(ProductSortPropertyItemResult result) {
        return new ProductSortPropertyItemResponse(
                result.name(),
                result.description()
        );
    }
}


