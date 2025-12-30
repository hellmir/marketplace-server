package com.personal.marketnote.common.utility;

import java.security.SecureRandom;

public class RandomCodeGenerator {
    private static final char[] REFERENCE_CODE_AALLOWED_LETTERS = "ABCDEFGHJKMNPQRTUVWXYZ".toCharArray();
    private static final char[] REFERENCE_CODE_ALLOWED_DIGITS = "2346789".toCharArray();
    private static final char[] ALL_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static final char[] ALL_DIGITS = "1234567890".toCharArray();

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateReferenceCode() {
        StringBuilder code = new StringBuilder(6);
        code.append(generateRandomLetter());
        code.append(generateRandomDigit());
        code.append(generateRandomLetter());
        code.append(generateRandomDigit());
        code.append(generateRandomLetter());
        code.append(generateRandomDigit());

        return code.toString();
    }

    private static char generateRandomLetter() {
        return REFERENCE_CODE_AALLOWED_LETTERS[SECURE_RANDOM.nextInt(REFERENCE_CODE_AALLOWED_LETTERS.length)];
    }

    private static char generateRandomDigit() {
        return REFERENCE_CODE_ALLOWED_DIGITS[SECURE_RANDOM.nextInt(REFERENCE_CODE_ALLOWED_DIGITS.length)];
    }

    public static String generateEmailVerificationCode() {
        return generateRandomCode(6);
    }

    private static String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(ALL_DIGITS[SECURE_RANDOM.nextInt(ALL_DIGITS.length)]);
        }

        return code.toString();
    }
}
