package com.personal.marketnote.product.port.in.result.product;

import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.product.domain.option.ProductOption;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductTag;
import com.personal.marketnote.product.port.in.result.option.ProductOptionItemResult;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ProductItemResult(
        Long id,
        Long sellerId,
        String name,
        String brandName,
        GetProductPricePolicyResult pricePolicy,
        Integer sales,
        List<ProductTag> productTags,
        GetFileResult catalogImage,
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
                .pricePolicy(GetProductPricePolicyResult.from(product.getDefaultPricePolicy()))
                .sales(product.getSales())
                .productTags(product.getProductTags())
                .orderNum(product.getOrderNum())
                .status(product.getStatus().name())
                .build();
    }

    public static ProductItemResult from(
            Product product,
            List<ProductOption> selectedOptions,
            PricePolicy pricePolicy
    ) {
        return ProductItemResult.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .pricePolicy(GetProductPricePolicyResult.from(pricePolicy))
                .sales(product.getSales())
                .productTags(product.getProductTags())
                .selectedOptions(selectedOptions.stream()
                        .map(ProductOptionItemResult::from)
                        .toList())
                .orderNum(product.getOrderNum())
                .status(product.getStatus().name())
                .build();
    }

    public static ProductItemResult from(ProductItemResult productItemResult, GetFileResult catalogImage) {
        return ProductItemResult.builder()
                .id(productItemResult.id())
                .sellerId(productItemResult.sellerId())
                .name(productItemResult.name())
                .brandName(productItemResult.brandName())
                .pricePolicy(productItemResult.pricePolicy())
                .sales(productItemResult.sales())
                .productTags(productItemResult.productTags())
                .catalogImage(catalogImage)
                .selectedOptions(productItemResult.selectedOptions())
                .orderNum(productItemResult.orderNum())
                .status(productItemResult.status())
                .build();
    }
}
