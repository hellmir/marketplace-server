package com.personal.marketnote.product.adapter.in.web.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

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

    @Schema(
            name = "price",
            description = "상품 기본 판매 가격(원)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long price;

    @Schema(
            name = "discountPrice",
            description = "상품 할인 판매 가격(원)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long discountPrice;

    @Schema(
            name = "accumulatedPoint",
            description = "상품 적립 포인트(원)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long accumulatedPoint;

    @Schema(
            name = "isFindAllOptions",
            description = "상품 목록 조회 시 옵션마다 개별 상품으로 조회 여부",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean isFindAllOptions;

    @Schema(
            name = "tags",
            description = "상품 태그 목록",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> tags;

    @Schema(
            name = "fulfillmentVendorGoods",
            description = "풀필먼트 서비스 벤더(현재 fassto) 상품 등록 옵션 정보",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Valid
    private RegisterProductFulfillmentVendorGoodsRequest fulfillmentVendorGoods;
}
