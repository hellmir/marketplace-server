package com.personal.marketnote.product.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductTag {
    private Long id;
    private Long productId;
    private String name;
    private Long orderNum;
    private EntityStatus status;

    @JsonCreator
    private static ProductTag jsonCreator(
            @JsonProperty("id") Long id,
            @JsonProperty("productId") Long productId,
            @JsonProperty("name") String name,
            @JsonProperty("orderNum") Long orderNum,
            @JsonProperty("status") EntityStatus status
    ) {
        return of(id, productId, name, orderNum, status);
    }

    public static ProductTag of(String name) {
        return ProductTag.builder()
                .name(name)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ProductTag of(Long productId, String name) {
        return ProductTag.builder()
                .productId(productId)
                .name(name)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ProductTag of(
            Long id, Long productId, String name, Long orderNum, EntityStatus status
    ) {
        return ProductTag.builder()
                .id(id)
                .productId(productId)
                .name(name)
                .orderNum(orderNum)
                .status(status)
                .build();
    }
}
