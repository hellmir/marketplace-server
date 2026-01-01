package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.ProductItemResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

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
                .orderNum(result.orderNum())
                .status(result.status())
                .build();
    }
}


