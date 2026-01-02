package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductTag;

import java.math.BigDecimal;
import java.util.List;

public record GetProductInfoResult(
        Long id,
        Long sellerId,
        String name,
        String brandName,
        String detail,
        Long price,
        Long discountPrice,
        BigDecimal discountRate,
        Long accumulatedPoint,
        Integer sales,
        Long viewCount,
        Long popularity,
        boolean findAllOptionsYn,
        List<ProductTag> productTags,
        Long orderNum,
        String status
) {
    public static GetProductInfoResult from(Product product) {
        return new GetProductInfoResult(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getBrandName(),
                product.getDetail(),
                product.getPrice(),
                product.getDiscountPrice(),
                product.getDiscountRate(),
                product.getAccumulatedPoint(),
                product.getSales(),
                product.getViewCount(),
                product.getPopularity(),
                product.isFindAllOptionsYn(),
                product.getProductTags(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }

    public static GetProductInfoResult fromAdjusted(
            Product product,
            Long price,
            Long discountPrice,
            Long accumulatedPoint
    ) {
        return new GetProductInfoResult(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getBrandName(),
                product.getDetail(),
                price,
                discountPrice,
                product.getDiscountRate(),
                accumulatedPoint,
                product.getSales(),
                product.getViewCount(),
                product.getPopularity(),
                product.isFindAllOptionsYn(),
                product.getProductTags(),
                product.getOrderNum(),
                product.getStatus().name()
        );
    }
}
