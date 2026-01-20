package com.personal.marketnote.product.adapter.in.web.pricepolicy.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

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

    @Schema(description = "가격정책이 적용될 옵션 ID 목록(조합). 단일 카테고리일 경우 생략", example = "[3, 7]")
    private List<Long> optionIds;
}
