package com.personal.marketnote.product.adapter.in.web.product.response;

import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.product.domain.product.ProductTag;
import com.personal.marketnote.product.port.in.result.option.ProductOptionItemResult;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import com.personal.marketnote.product.port.in.result.product.AdminProductItemResult;
import com.personal.marketnote.product.port.in.result.product.ProductItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class AdminProductItemResponse {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private GetProductPricePolicyResult pricePolicy;
    private Integer sales;
    private List<ProductTag> productTags;
    private GetFileResult catalogImage;
    private List<ProductOptionItemResult> selectedOptions;
    private Integer stock;
    private Float averageRating;
    private Integer totalCount;
    private String status;
    private Long orderNum;
    private FasstoGoodsItemResponse fasstoGoods;

    public static AdminProductItemResponse from(AdminProductItemResult result) {
        ProductItemResult product = result.product();

        return AdminProductItemResponse.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .pricePolicy(product.getPricePolicy())
                .sales(product.getSales())
                .productTags(product.getProductTags())
                .catalogImage(product.getCatalogImage())
                .selectedOptions(product.getSelectedOptions())
                .stock(product.getStock())
                .averageRating(product.getAverageRating())
                .totalCount(product.getTotalCount())
                .status(product.getStatus())
                .orderNum(product.getOrderNum())
                .fasstoGoods(
                        result.fasstoGoodsInfo() != null
                                ? FasstoGoodsItemResponse.from(result.fasstoGoodsInfo())
                                : null
                )
                .build();
    }
}
