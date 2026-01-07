package com.personal.marketnote.commerce.adapter.in.client.order.request;

import com.personal.marketnote.commerce.domain.order.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ChangeOrderStatusRequest {
    @Schema(
            name = "pricePolicyIds",
            description = "가격 정책 ID 목록",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<Long> pricePolicyIds;

    @Schema(
            name = "orderStatus",
            description = "주문 상태",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private OrderStatus orderStatus;
}
