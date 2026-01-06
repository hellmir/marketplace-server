package com.personal.marketnote.commerce.adapter.in.client.order.request.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterInventoryRequest {
    @Schema(
            name = "pricePolicyId",
            description = "가격 정책 ID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "가격 정책 ID는 필수값입니다.")
    @Min(value = 1, message = "가격 정책 ID는 1 이상이어야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "가격 정책 ID는 정수형 최대값을 초과할 수 없습니다.")
    private Long pricePolicyId;
}

