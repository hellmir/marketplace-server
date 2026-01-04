package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductTag;
import lombok.AccessLevel;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
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
        List<ProductOptionItemResult> selectedOptions,
        Long orderNum,
        String status
) {
    public static ProductItemResult from(Product product) {
        return ProductItemResult.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .discountRate(product.getDiscountRate())
                .accumulatedPoint(product.getAccumulatedPoint())
                .sales(product.getSales())
                .productTags(product.getProductTags())
                .orderNum(product.getOrderNum())
                .status(product.getStatus().name())
                .build();
    }

    public static ProductItemResult from(
            Product product,
            List<ProductOption> selectedOptions,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint
    ) {
        return ProductItemResult.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .sales(product.getSales())
                .productTags(product.getProductTags())
                .selectedOptions(selectedOptions.stream()
                        .map(ProductOptionItemResult::from)
                        .toList())
                .orderNum(product.getOrderNum())
                .status(product.getStatus().name())
                .build();
    }

    public static ProductItemResult withCatalogImages(ProductItemResult productItemResult, GetFilesResult catalogImages) {
        return ProductItemResult.builder()
                .id(productItemResult.id())
                .sellerId(productItemResult.sellerId())
                .name(productItemResult.name())
                .brandName(productItemResult.brandName())
                .price(productItemResult.price())
                .discountPrice(productItemResult.discountPrice())
                .discountRate(productItemResult.discountRate())
                .accumulatedPoint(productItemResult.accumulatedPoint())
                .sales(productItemResult.sales())
                .productTags(productItemResult.productTags())
                .catalogImages(catalogImages)
                .selectedOptions(productItemResult.selectedOptions())
                .orderNum(productItemResult.orderNum())
                .status(productItemResult.status())
                .build();
    }
}
