package com.personal.marketnote.product.adapter.in.client.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterProductRequest {
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
            name = "name",
            description = "상품명",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            name = "brandName",
            description = "브랜드명",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String brandName;

    @Schema(
            name = "detail",
            description = "상품 설명",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @NotBlank(message = "상품 설명은 필수값입니다.")
    private String detail;
}


