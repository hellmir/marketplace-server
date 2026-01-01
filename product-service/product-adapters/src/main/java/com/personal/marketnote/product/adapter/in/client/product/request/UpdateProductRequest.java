package com.personal.marketnote.product.adapter.in.client.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProductRequest {
    @Schema(
            name = "name",
            description = "상품명",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 1, max = 255, message = "상품명은 1~255자입니다.")
    private String name;

    @Schema(
            name = "brandName",
            description = "브랜드명",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 255, message = "브랜드명은 최대 255자입니다.")
    private String brandName;

    @Schema(
            name = "detail",
            description = "상품 설명",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 1023, message = "상품 설명은 최대 1023자입니다.")
    private String detail;
}


