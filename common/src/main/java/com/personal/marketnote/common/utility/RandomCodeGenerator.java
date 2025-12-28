package com.personal.marketnote.common.utility;

import java.security.SecureRandom;

public class RandomCodeGenerator {
    private static final char[] ALLOWED_LETTERS = "ABCDEFGHJKMNPQRTUVWXYZ".toCharArray();
    private static final char[] ALLOWED_DIGITS = "2346789".toCharArray();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static char randomLetter() {
        return ALLOWED_LETTERS[SECURE_RANDOM.nextInt(ALLOWED_LETTERS.length)];
    }

    private static char randomDigit() {
        return ALLOWED_DIGITS[SECURE_RANDOM.nextInt(ALLOWED_DIGITS.length)];
    }

    public static String generateSignupCode() {
        StringBuilder code = new StringBuilder(6);
        code.append(randomLetter());
        code.append(randomDigit());
        code.append(randomLetter());
        code.append(randomDigit());
        code.append(randomLetter());
        code.append(randomDigit());

        return code.toString();
    }
}
