package com.personal.marketnote.commerce.adapter.in.client.order.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterOrderRequest {
    @Schema(
            name = "sellerId",
            description = "판매자 회원 ID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "판매자 회원 ID는 필수값입니다.")
    @Min(value = 1, message = "판매자 회원 ID는 1 이상이어야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "판매자 회원 ID는 정수형 최대값을 초과할 수 없습니다.")
    private Long sellerId;

    @Schema(
            name = "totalAmount",
            description = "총 주문 금액(원)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "총 주문 금액은 필수값입니다.")
    @Min(value = 0, message = "총 주문 금액은 0 이상이어야 합니다.")
    private Long totalAmount;

    @Schema(
            name = "couponAmount",
            description = "쿠폰 할인 금액(원)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long couponAmount;

    @Schema(
            name = "pointAmount",
            description = "포인트 사용 금액(원)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long pointAmount;

    @Schema(
            name = "orderProducts",
            description = "주문 상품 목록",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "주문 상품 목록은 필수값입니다.")
    @Valid
    private List<OrderProductItemRequest> orderProducts;

    @Getter
    public static class OrderProductItemRequest {
        @Schema(
                name = "pricePolicyId",
                description = "가격 정책 ID",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "가격 정책 ID는 필수값입니다.")
        @Min(value = 1, message = "가격 정책 ID는 1 이상이어야 합니다.")
        @Max(value = Long.MAX_VALUE, message = "가격 정책 ID는 정수형 최대값을 초과할 수 없습니다.")
        private Long pricePolicyId;

        @Schema(
                name = "quantity",
                description = "주문 수량",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "주문 수량은 필수값입니다.")
        @Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
        private Integer quantity;

        @Schema(
                name = "unitAmount",
                description = "단가(원)",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "단가는 필수값입니다.")
        @Min(value = 0, message = "단가는 0 이상이어야 합니다.")
        private Long unitAmount;

        @Schema(
                name = "imageUrl",
                description = "상품 이미지 URL",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        private String imageUrl;
    }
}

