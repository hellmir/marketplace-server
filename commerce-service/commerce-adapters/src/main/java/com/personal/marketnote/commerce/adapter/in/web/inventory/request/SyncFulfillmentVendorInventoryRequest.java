package com.personal.marketnote.commerce.adapter.in.web.inventory.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class SyncFulfillmentVendorInventoryRequest {
    @Schema(
            name = "inventories",
            description = "풀필먼트 벤더 재고 목록",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "재고 목록은 필수값입니다.")
    @Valid
    private List<SyncFulfillmentVendorInventoryItemRequest> inventories;
}
