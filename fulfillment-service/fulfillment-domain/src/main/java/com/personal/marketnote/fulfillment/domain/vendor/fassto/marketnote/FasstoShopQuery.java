package com.personal.marketnote.fulfillment.domain.vendor.fassto.shop;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoShopQuery {
    private String customerCode;
    private String accessToken;

    public static FasstoShopQuery of(String customerCode, String accessToken) {
        FasstoShopQuery query = FasstoShopQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .build();
        query.validate();
        return query;
    }

    private void validate() {
        if (FormatValidator.hasNoValue(customerCode)) {
            throw new IllegalArgumentException("customerCode is required.");
        }
        if (FormatValidator.hasNoValue(accessToken)) {
            throw new IllegalArgumentException("accessToken is required.");
        }
    }
}
