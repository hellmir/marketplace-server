package com.oponiti.shopreward.common.exception.novalue;

public class EmailNoValueException extends NoValueException {
    private static final String EMAIL_NO_VALUE_EXCEPTION_MESSAGE = "이메일 값이 없습니다.";

    public EmailNoValueException() {
        super(EMAIL_NO_VALUE_EXCEPTION_MESSAGE);
    }
}
