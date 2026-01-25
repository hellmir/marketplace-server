package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;

import java.io.IOException;

public class FasstoAuthDisconnectFailedException extends ExternalOperationFailedException {
    private static final String FASSTO_AUTH_DISCONNECT_FAILED_EXCEPTION_MESSAGE =
            "Fassto authentication disconnect request failed.";

    public FasstoAuthDisconnectFailedException(IOException cause) {
        super(FASSTO_AUTH_DISCONNECT_FAILED_EXCEPTION_MESSAGE, cause);
    }
}
