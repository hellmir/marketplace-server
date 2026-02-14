package com.personal.marketnote.fulfillment.exception;

import com.personal.marketnote.common.exception.ExternalOperationFailedException;
import com.personal.marketnote.common.utility.FormatValidator;

import java.io.IOException;

public class GetFasstoWarehousingDetailFailedException extends ExternalOperationFailedException {
    private static final String DEFAULT_MESSAGE = "파스토 입고 상세 조회 중 오류가 발생했습니다.";

    public GetFasstoWarehousingDetailFailedException(IOException cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public GetFasstoWarehousingDetailFailedException(String vendorMessage, IOException cause) {
        super(resolveMessage(vendorMessage), cause);
    }

    private static String resolveMessage(String vendorMessage) {
        if (FormatValidator.hasValue(vendorMessage)) {
            return String.format("파스토 입고 상세 조회 실패: %s", vendorMessage);
        }
        return DEFAULT_MESSAGE;
    }
}
