package com.personal.marketnote.product.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.BaseDomain;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Product extends BaseDomain {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private String detail;
    private PricePolicy defaultPricePolicy;
    private Integer sales;
    private Long viewCount;
    private Long popularity;
    private boolean findAllOptionsYn;
    private List<ProductTag> productTags;
    private Long orderNum;

    @JsonCreator
    private static Product jsonCreator(
            @JsonProperty("id") Long id,
            @JsonProperty("sellerId") Long sellerId,
            @JsonProperty("name") String name,
            @JsonProperty("brandName") String brandName,
            @JsonProperty("detail") String detail,
            @JsonProperty("defaultPricePolicy") PricePolicy defaultPricePolicy,
            @JsonProperty("sales") Integer sales,
            @JsonProperty("viewCount") Long viewCount,
            @JsonProperty("popularity") Long popularity,
            @JsonProperty("findAllOptionsYn") boolean findAllOptionsYn,
            @JsonProperty("productTags") List<ProductTag> productTags,
            @JsonProperty("orderNum") Long orderNum,
            @JsonProperty("status") EntityStatus status
    ) {
        return of(
                id, sellerId, name, brandName, detail, defaultPricePolicy, sales, viewCount,
                popularity, findAllOptionsYn, productTags, orderNum, status
        );
    }

    public static Product of(
            Long sellerId, String name, String brandName, String detail, boolean isFindAllOptions, List<String> tags
    ) {
        return Product.builder()
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .findAllOptionsYn(isFindAllOptions)
                .productTags(
                        tags.stream()
                                .map(ProductTag::of)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static Product of(
            Long id, Long sellerId, String name, String brandName, String detail, PricePolicy defaultPricePolicy,
            Integer sales, Long viewCount, Long popularity, boolean findAllOptionsYn, List<ProductTag> productTags,
            Long orderNum, EntityStatus status
    ) {
        Product product = Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .defaultPricePolicy(defaultPricePolicy)
                .sales(sales)
                .viewCount(viewCount)
                .popularity(popularity)
                .findAllOptionsYn(findAllOptionsYn)
                .productTags(productTags)
                .orderNum(orderNum)
                .build();

        if (status.isActive()) {
            product.activate();
            return product;
        }

        if (status.isInactive()) {
            product.deactivate();
            return product;
        }

        product.hide();
        return product;
    }

    public static Product of(
            Long id, Long sellerId, String name, String brandName, String detail,
            Integer sales, Long viewCount, Long popularity, boolean findAllOptionsYn, List<ProductTag> productTags,
            Long orderNum, EntityStatus status
    ) {
        Product product = Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .sales(sales)
                .viewCount(viewCount)
                .popularity(popularity)
                .findAllOptionsYn(findAllOptionsYn)
                .productTags(productTags)
                .orderNum(orderNum)
                .build();

        if (status.isActive()) {
            product.activate();
            return product;
        }

        if (status.isInactive()) {
            product.deactivate();
            return product;
        }

        product.hide();
        return product;
    }

    public void update(String name, String brandName, String detail, boolean isFindAllOptions, List<String> tags) {
        this.name = name;
        this.brandName = brandName;
        this.detail = detail;
        findAllOptionsYn = isFindAllOptions;
        productTags = tags.stream()
                .map(tag -> ProductTag.of(id, tag))
                .toList();
    }

    public boolean isActive() {
        return status.isActive();
    }

    public void delete() {
        status = EntityStatus.INACTIVE;
    }
}
