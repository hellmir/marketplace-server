package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;

import java.io.IOException;

public class UpdateFasstoSupplierFailedException extends ExternalOperationFailedException {
    private static final String UPDATE_FASSTO_SUPPLIER_FAILED_EXCEPTION_MESSAGE =
            "Fassto supplier update request failed.";

    public UpdateFasstoSupplierFailedException(IOException cause) {
        super(UPDATE_FASSTO_SUPPLIER_FAILED_EXCEPTION_MESSAGE, cause);
    }

    public UpdateFasstoSupplierFailedException(String vendorMessage, IOException cause) {
        super(resolveMessage(vendorMessage), cause);
    }

    private static String resolveMessage(String vendorMessage) {
        if (vendorMessage == null || vendorMessage.isBlank()) {
            return UPDATE_FASSTO_SUPPLIER_FAILED_EXCEPTION_MESSAGE;
        }
        return vendorMessage;
    }
}
