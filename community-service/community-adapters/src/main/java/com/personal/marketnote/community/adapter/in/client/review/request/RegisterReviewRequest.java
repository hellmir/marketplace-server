package com.personal.marketnote.community.adapter.in.client.review.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RegisterReviewRequest {
    @Schema(
            name = "orderId",
            description = "주문 ID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "주문 ID는 필수값입니다.")
    @Min(value = 1, message = "주문 ID는 1 이상이어야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "주문 ID는 정수형 최대값을 초과할 수 없습니다.")
    private Long orderId;

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
            name = "score",
            description = "리뷰 평점(1 ~ 5 정수)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "리뷰 평점은 필수값입니다.")
    @DecimalMin(value = "0.0", message = "리뷰 평점은 0.0 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "리뷰 평점은 5.0 이하여야 합니다.")
    @Digits(integer = 1, fraction = 0, message = "리뷰 평점은 정수만 입력 가능합니다.")
    private Float score;

    @Schema(
            name = "content",
            description = "리뷰 내용",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "리뷰 내용은 필수값입니다.")
    @Size(min = 10, max = 8192, message = "리뷰 내용은 10자 이상 8192자 이하로 입력해야 합니다.")
    private String content;
}
