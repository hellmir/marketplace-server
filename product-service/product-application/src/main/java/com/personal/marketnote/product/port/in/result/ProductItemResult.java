package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;

public record ProductItemResult(
        Long id,
        Long sellerId,
        String name,
        String brandName,
        Long currentPrice,
        Long accumulatedPoint,
        Integer sales,
        Long orderNum,
        String status
) {
    public static ProductItemResult from(Product product) {
        return new ProductItemResult(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getBrandName(),
                product.getCurrentPrice(),
                product.getAccumulatedPoint(),
                product.getSales(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }
}


