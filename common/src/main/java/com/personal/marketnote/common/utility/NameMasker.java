package com.personal.marketnote.common.utility;

import static com.personal.marketnote.common.utility.CharacterConstant.WILD_CARD;

public class NameMasker {
    public static String mask(String value) {
        if (!FormatValidator.hasValue(value)) {
            return value;
        }

        int length = value.length();
        String firstChar = value.substring(0, 1);

        if (length < 3) {
            return firstChar + WILD_CARD;
        }

        String lastChar = value.substring(length - 1);

        return firstChar + WILD_CARD + lastChar;
    }
}
