package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;

import java.io.IOException;

public class RegisterFasstoWarehouseFailedException extends ExternalOperationFailedException {
    private static final String REGISTER_FASSTO_WAREHOUSE_FAILED_EXCEPTION_MESSAGE =
            "Fassto warehouse registration request failed.";

    public RegisterFasstoWarehouseFailedException(IOException cause) {
        super(REGISTER_FASSTO_WAREHOUSE_FAILED_EXCEPTION_MESSAGE, cause);
    }
}
