package com.personal.marketnote.fulfillment.adapter.out.web.commerce.request;

import com.personal.marketnote.fulfillment.port.out.commerce.UpdateCommerceInventoryItemCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncFulfillmentVendorInventoryItemRequest {
    private Long productId;
    private Integer stock;

    public static SyncFulfillmentVendorInventoryItemRequest from(UpdateCommerceInventoryItemCommand command) {
        return SyncFulfillmentVendorInventoryItemRequest.builder()
                .productId(command.productId())
                .stock(command.stock())
                .build();
    }
}
