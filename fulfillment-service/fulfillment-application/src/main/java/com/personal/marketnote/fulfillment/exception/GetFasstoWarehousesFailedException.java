package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;

import java.io.IOException;

public class GetFasstoWarehousesFailedException extends ExternalOperationFailedException {
    private static final String GET_FASSTO_WAREHOUSE_LIST_FAILED_EXCEPTION_MESSAGE =
            "Fassto warehouse list request failed.";

    public GetFasstoWarehousesFailedException(IOException cause) {
        super(GET_FASSTO_WAREHOUSE_LIST_FAILED_EXCEPTION_MESSAGE, cause);
    }

    public GetFasstoWarehousesFailedException(String vendorMessage, IOException cause) {
        super(resolveMessage(vendorMessage), cause);
    }

    private static String resolveMessage(String vendorMessage) {
        if (vendorMessage == null || vendorMessage.isBlank()) {
            return GET_FASSTO_WAREHOUSE_LIST_FAILED_EXCEPTION_MESSAGE;
        }
        return vendorMessage;
    }
}
