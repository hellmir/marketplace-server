package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.ProductOptionItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductOptionResponse {
    private Long id;
    private String content;
    private Long price;
    private Long accumulatedPoint;
    private String status;

    public static ProductOptionResponse from(ProductOptionItemResult result) {
        return ProductOptionResponse.builder()
                .id(result.id())
                .content(result.content())
                .price(result.price())
                .accumulatedPoint(result.accumulatedPoint())
                .status(result.status())
                .build();
    }
}


