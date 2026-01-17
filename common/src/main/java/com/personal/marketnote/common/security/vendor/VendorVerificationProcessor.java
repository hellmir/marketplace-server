package com.personal.marketnote.common.security.vendor;

import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.utility.FormatValidator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class VendorVerificationProcessor {
    public static void validateAdpopcornSignature(String secret, String text, String signature) {
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacMD5"));
            byte[] result = mac.doFinal(text.getBytes(StandardCharsets.UTF_8));

            if (!FormatValidator.equalsIgnoreCase(toHex(result), signature)) {
                throw new VendorVerificationFailedException("애드팝콘 리워드 지급 연동 중 signed value 검증에 실패했습니다.");
            }
        } catch (Exception e) {
            throw new VendorVerificationFailedException("invalid signed value");
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
