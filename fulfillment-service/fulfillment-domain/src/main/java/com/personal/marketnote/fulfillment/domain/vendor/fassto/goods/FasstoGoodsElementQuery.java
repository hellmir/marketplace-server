package com.personal.marketnote.fulfillment.domain.vendor.fassto.goods;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoGoodsElementQuery {
    private String customerCode;
    private String accessToken;

    public static FasstoGoodsElementQuery of(String customerCode, String accessToken) {
        FasstoGoodsElementQuery query = FasstoGoodsElementQuery.builder()
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
