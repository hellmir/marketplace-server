package com.personal.marketnote.product.adapter.in.client.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterProductCategoriesRequest {
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
            name = "categoryIds",
            description = "등록할 카테고리 ID 목록",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "카테고리 ID 목록은 필수입니다.")
    private List<Long> categoryIds;
}
