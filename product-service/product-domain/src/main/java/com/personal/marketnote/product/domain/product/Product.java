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
        return from(
                ProductSnapshotState.builder()
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
                        .status(status)
                        .build()
        );
    }

    public static Product from(ProductCreateState state) {
        List<ProductTag> tags = state.getTags() == null
                ? List.of()
                : state.getTags()
                .stream()
                .map(ProductTag::from)
                .collect(Collectors.toList());

        return Product.builder()
                .sellerId(state.getSellerId())
                .name(state.getName())
                .brandName(state.getBrandName())
                .detail(state.getDetail())
                .findAllOptionsYn(state.isFindAllOptionsYn())
                .productTags(tags)
                .build();
    }

    public static Product from(ProductSnapshotState state) {
        Product product = Product.builder()
                .id(state.getId())
                .sellerId(state.getSellerId())
                .name(state.getName())
                .brandName(state.getBrandName())
                .detail(state.getDetail())
                .defaultPricePolicy(state.getDefaultPricePolicy())
                .sales(state.getSales())
                .viewCount(state.getViewCount())
                .popularity(state.getPopularity())
                .findAllOptionsYn(state.isFindAllOptionsYn())
                .productTags(state.getProductTags())
                .orderNum(state.getOrderNum())
                .build();

        if (state.getDefaultPricePolicy() != null) {
            state.getDefaultPricePolicy().addProduct(product);
        }

        EntityStatus status = state.getStatus();
        if (status != null) {
            if (status.isActive()) {
                product.activate();
            } else if (status.isInactive()) {
                product.deactivate();
            } else {
                product.hide();
            }
        }

        return product;
    }

    public void update(String name, String brandName, String detail, boolean isFindAllOptions, List<String> tags) {
        this.name = name;
        this.brandName = brandName;
        this.detail = detail;
        findAllOptionsYn = isFindAllOptions;
        productTags = tags.stream()
                .map(tag -> ProductTag.from(
                        ProductTagCreateState.builder()
                                .productId(id)
                                .name(tag)
                                .build()
                ))
                .toList();
    }

    public boolean isActive() {
        return status.isActive();
    }

    public void delete() {
        status = EntityStatus.INACTIVE;
    }
}
