package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;

import java.math.BigDecimal;

public record ProductItemResult(
        Long id,
        Long sellerId,
        String name,
        String brandName,
        Long price,
        Long discountPrice,
        BigDecimal discountRate,
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
                product.getPrice(),
                product.getDiscountPrice(),
                product.getDiscountRate(),
                product.getAccumulatedPoint(),
                product.getSales(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }

    public static ProductItemResult ofVariant(
            Product base,
            String variantName,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint
    ) {
        return new ProductItemResult(
                base.getId(),
                base.getSellerId(),
                variantName,
                base.getBrandName(),
                price,
                discountPrice,
                discountRate,
                accumulatedPoint,
                base.getSales(),
                base.getOrderNum(),
                base.getStatus().name()
        );
    }
}


