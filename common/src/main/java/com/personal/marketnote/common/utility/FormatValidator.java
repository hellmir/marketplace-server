package com.personal.marketnote.common.utility;

import java.util.Collection;
import java.util.regex.Pattern;

import static com.personal.marketnote.common.utility.NumberConstant.MINUS_ONE;

public class FormatValidator {
    public static boolean isValid(CharSequence value, Pattern pattern) {
        return hasValue(value) && hasValue(pattern) && pattern.matcher(value).matches();
    }

    public static boolean hasValue(Object value) {
        return value != null && !value.toString().isBlank();
    }

    public static boolean hasValue(Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean hasValue(Number number) {
        return number != null && !equals(number.toString(), MINUS_ONE);
    }

    public static boolean equals(Object value1, Object value2) {
        return hasValue(value1) && hasValue(value2) && value1.equals(value2);
    }
}
