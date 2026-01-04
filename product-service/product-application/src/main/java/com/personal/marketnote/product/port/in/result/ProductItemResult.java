package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
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
        GetFilesResult catalogImages,
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
                null,
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
                null,
                product.getOrderNum(),
                product.getStatus().name()
        );
    }

    public static ProductItemResult withCatalogImages(ProductItemResult base, GetFilesResult catalogImages) {
        return new ProductItemResult(
                base.id(),
                base.sellerId(),
                base.name(),
                base.brandName(),
                base.price(),
                base.discountPrice(),
                base.discountRate(),
                base.accumulatedPoint(),
                base.sales(),
                base.productTags(),
                catalogImages,
                base.orderNum(),
                base.status()
        );
    }
}
