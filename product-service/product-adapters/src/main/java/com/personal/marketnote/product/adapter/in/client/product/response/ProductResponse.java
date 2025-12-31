package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.ProductItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {
    private Long id;
    private Long sellerId;
    private String name;
    private String detail;
    private Integer sales;
    private Long orderNum;
    private String status;

    public static ProductResponse from(ProductItemResult result) {
        return ProductResponse.builder()
                .id(result.id())
                .sellerId(result.sellerId())
                .name(result.name())
                .detail(result.detail())
                .sales(result.sales())
                .orderNum(result.orderNum())
                .status(result.status())
                .build();
    }
}


