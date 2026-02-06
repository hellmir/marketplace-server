package com.personal.marketnote.fulfillment.adapter.out.web.commerce.request;

import com.personal.marketnote.fulfillment.port.out.commerce.UpdateCommerceInventoryCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncFulfillmentVendorInventoryRequest {
    private List<SyncFulfillmentVendorInventoryItemRequest> inventories;

    public static SyncFulfillmentVendorInventoryRequest from(UpdateCommerceInventoryCommand command) {
        List<SyncFulfillmentVendorInventoryItemRequest> items = command.inventories().stream()
                .map(SyncFulfillmentVendorInventoryItemRequest::from)
                .toList();
        return SyncFulfillmentVendorInventoryRequest.builder()
                .inventories(items)
                .build();
    }
}
