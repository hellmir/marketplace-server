package com.personal.marketnote.product.adapter.in.client.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterPricePolicyRequest {
    @Schema(description = "정가", requiredMode = Schema.RequiredMode.REQUIRED, example = "45000")
    @NotNull
    private Long price;

    @Schema(description = "현재 판매가", requiredMode = Schema.RequiredMode.REQUIRED, example = "37000")
    @NotNull
    private Long discountPrice;

    @Schema(description = "적립 포인트", example = "1200")
    @NotNull
    private Long accumulatedPoint;

    // 할인율/적립율은 서버에서 계산
}


