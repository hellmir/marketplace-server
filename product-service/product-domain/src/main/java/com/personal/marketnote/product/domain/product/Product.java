package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.BaseDomain;
import lombok.*;

import java.math.BigDecimal;
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
    private Long price;
    private Long discountPrice;
    private BigDecimal discountRate;
    private Long accumulatedPoint;
    private Integer sales;
    private Long viewCount;
    private Long popularity;
    private boolean findAllOptionsYn;
    private List<ProductTag> productTags;
    private Long orderNum;

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
            Long id, Long sellerId, String name, String brandName, String detail, Long price, Long discountPrice,
            BigDecimal discountRate, Long accumulatedPoint, Integer sales, Long viewCount, Long popularity,
            boolean findAllOptionsYn, List<ProductTag> productTags, Long orderNum, EntityStatus status
    ) {
        Product product = Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
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
