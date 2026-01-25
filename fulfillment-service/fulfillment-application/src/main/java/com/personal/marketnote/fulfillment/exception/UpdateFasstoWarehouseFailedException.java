package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;

import java.io.IOException;

public class UpdateFasstoWarehouseFailedException extends ExternalOperationFailedException {
    private static final String UPDATE_FASSTO_WAREHOUSE_FAILED_EXCEPTION_MESSAGE =
            "Fassto warehouse update request failed.";

    public UpdateFasstoWarehouseFailedException(IOException cause) {
        super(UPDATE_FASSTO_WAREHOUSE_FAILED_EXCEPTION_MESSAGE, cause);
    }
}
