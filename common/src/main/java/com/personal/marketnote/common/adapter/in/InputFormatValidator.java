package com.personal.marketnote.common.adapter.in;

import com.personal.marketnote.common.utility.FormatValidator;

import java.util.regex.Pattern;

import static com.personal.marketnote.common.utility.RegularExpressionConstant.POSITIVE_INTEGER_PATTERN;

public class InputFormatValidator {
    static final String ID_NO_VALUE_EXCEPTION = "ID는 필수값입니다.";
    static final String INVALID_ID_EXCEPTION = "ID는 양의 정수(1 이상의 숫자값)여야 합니다. 입력된 ID: ";

    public static void validateId(String id) {
        checkIdIsNotBlank(id);
        checkIdPattern(id);
    }

    private static void checkIdIsNotBlank(String id) {
        if (!FormatValidator.hasValue(id)) {
            throw new IllegalArgumentException(ID_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkIdPattern(String id) {
        if (!FormatValidator.isValid(id, Pattern.compile(POSITIVE_INTEGER_PATTERN))) {
            throw new IllegalArgumentException(INVALID_ID_EXCEPTION + id);
        }
    }
}
