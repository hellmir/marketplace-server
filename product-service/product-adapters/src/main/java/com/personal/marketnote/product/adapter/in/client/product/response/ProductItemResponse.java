package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.product.domain.product.ProductTag;
import com.personal.marketnote.product.port.in.result.option.ProductOptionItemResult;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import com.personal.marketnote.product.port.in.result.product.ProductItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductItemResponse {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private GetProductPricePolicyResult pricePolicy;
    private Integer sales;
    private List<ProductTag> productTags;
    private GetFileResult catalogImage;
    private List<ProductOptionItemResult> selectedOptions;
    private Long orderNum;
    private String status;

    public static ProductItemResponse from(ProductItemResult result) {
        return ProductItemResponse.builder()
                .id(result.id())
                .sellerId(result.sellerId())
                .name(result.name())
                .brandName(result.brandName())
                .pricePolicy(result.pricePolicy())
                .sales(result.sales())
                .productTags(result.productTags())
                .catalogImage(result.catalogImage())
                .selectedOptions(result.selectedOptions())
                .orderNum(result.orderNum())
                .status(result.status())
                .build();
    }
}


