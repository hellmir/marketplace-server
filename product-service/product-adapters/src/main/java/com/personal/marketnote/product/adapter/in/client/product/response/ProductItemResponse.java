package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.ProductTag;
import com.personal.marketnote.product.port.in.result.option.ProductOptionItemResult;
import com.personal.marketnote.product.port.in.result.product.ProductItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductItemResponse {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private Long price;
    private Long discountPrice;
    private BigDecimal discountRate;
    private Long accumulatedPoint;
    private Integer sales;
    private List<ProductTag> productTags;
    private CatalogImagesResponse catalogImages;
    private List<ProductOptionItemResult> selectedOptions;
    private Long orderNum;
    private String status;

    public static ProductItemResponse from(ProductItemResult result) {
        return ProductItemResponse.builder()
                .id(result.id())
                .sellerId(result.sellerId())
                .name(result.name())
                .brandName(result.brandName())
                .price(result.price())
                .discountPrice(result.discountPrice())
                .discountRate(result.discountRate())
                .accumulatedPoint(result.accumulatedPoint())
                .sales(result.sales())
                .productTags(result.productTags())
                .catalogImages(CatalogImagesResponse.from(result.catalogImages()))
                .selectedOptions(result.selectedOptions())
                .orderNum(result.orderNum())
                .status(result.status())
                .build();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    public static class CatalogImagesResponse {
        private List<CatalogImageItemResponse> images;

        public static CatalogImagesResponse from(GetFilesResult getFilesResult) {
            if (FormatValidator.hasValue(getFilesResult) && FormatValidator.hasValue(getFilesResult)) {
                return CatalogImagesResponse.builder()
                        .images(getFilesResult.images()
                                .stream()
                                .map(CatalogImageItemResponse::from).toList())
                        .build();
            }

            return new CatalogImagesResponse(List.of());
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    public static class CatalogImageItemResponse {
        private Long id;
        private String sort;
        private String extension;
        private String name;
        private String s3Url;
        private List<String> resizedS3Urls;
        private Long orderNum;

        public static CatalogImageItemResponse from(GetFilesResult.FileItem item) {
            return CatalogImageItemResponse.builder()
                    .id(item.id())
                    .sort(item.sort())
                    .extension(item.extension())
                    .name(item.name())
                    .s3Url(item.s3Url())
                    .resizedS3Urls(item.resizedS3Urls())
                    .orderNum(item.orderNum())
                    .build();
        }
    }
}


