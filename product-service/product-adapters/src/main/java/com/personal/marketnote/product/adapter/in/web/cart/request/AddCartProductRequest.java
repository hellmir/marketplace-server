package com.personal.marketnote.product.adapter.in.web.cart.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartProductRequest(
        @Schema(description = "가격 정책 ID")
        @NotNull(message = "가격 정책 ID는 필수입니다.")
        @Min(value = 1, message = "가격 정책 ID는 1 이상이어야 합니다.")
        @Max(value = Long.MAX_VALUE, message = "가격 정책 ID는 정수형 최대값을 초과할 수 없습니다.")
        Long pricePolicyId,

        @Schema(
                name = "sharerId",
                description = "링크 공유 회원 ID",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Min(value = 1, message = "링크 공유 회원 ID는 1 이상이어야 합니다.")
        @Max(value = Long.MAX_VALUE, message = "링크 공유 회원 ID는 정수형 최대값을 초과할 수 없습니다.")
        Long sharerId,

        @Schema(description = "상품 이미지 URL")
        String imageUrl,

        @Schema(description = "상품 수량")
        @NotNull(message = "상품 수량은 필수입니다.")
        @Min(value = 1, message = "상품 수량은 1 이상이어야 합니다.")
        @Max(value = Short.MAX_VALUE, message = "상품 수량은 정수형 최대값을 초과할 수 없습니다.")
        Short quantity
) {
}
