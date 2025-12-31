package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.GetProductsResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetProductsResponse {
    private List<ProductResponse> products;

    public static GetProductsResponse from(GetProductsResult result) {
        return GetProductsResponse.builder()
                .products(
                        result.products().stream()
                                .map(ProductResponse::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}


