package com.personal.marketnote.product.adapter.in.client.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterProductOptionRequest {
    @Schema(name = "content", description = "옵션 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "옵션 내용은 필수입니다.")
    private String content;

    @Schema(name = "price", description = "옵션 가격(원)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long price;

    @Schema(name = "accumulatedPoint", description = "적립 포인트(원)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long accumulatedPoint;
}


