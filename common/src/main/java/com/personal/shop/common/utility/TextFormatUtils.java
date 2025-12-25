package com.personal.shop.common.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextFormatUtils {

    public static String normalizeUrl(String url) {
        if (url != null && !url.trim().isEmpty() && !url.toLowerCase().startsWith("http")) {
            return "https://" + url.trim();
        }
        return url;
    }

    public static String normalizePhoneNumber(String tel) {
        if (tel == null) {
            return null;
        }
        String cleanedTel = tel.replaceAll("-", "");
        if (cleanedTel.length() == 11) {
            return String.format("%s-%s-%s", cleanedTel.substring(0, 3), cleanedTel.substring(3, 7), cleanedTel.substring(7));
        }
        if (cleanedTel.length() == 10) {
            return String.format("%s-%s-%s", cleanedTel.substring(0, 3), cleanedTel.substring(3, 6), cleanedTel.substring(6));
        }
        return tel; // 원본 반환
    }
} 
