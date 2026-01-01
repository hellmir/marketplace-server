package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductTag;

import java.math.BigDecimal;
import java.util.List;

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
        List<ProductTag> productTags,
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
                product.getProductTags(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }

    public static ProductItemResult from(
            Product product,
            String variantName,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint
    ) {
        return new ProductItemResult(
                product.getId(),
                product.getSellerId(),
                variantName,
                product.getBrandName(),
                price,
                discountPrice,
                discountRate,
                accumulatedPoint,
                product.getSales(),
                product.getProductTags(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }
}


