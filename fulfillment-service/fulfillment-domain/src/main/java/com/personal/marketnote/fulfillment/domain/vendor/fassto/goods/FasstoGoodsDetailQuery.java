package com.personal.marketnote.fulfillment.domain.vendor.fassto.goods;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoGoodsDetailQuery {
    private String customerCode;
    private String accessToken;
    private String godNm;

    public static FasstoGoodsDetailQuery of(
            String customerCode,
            String accessToken,
            String godNm
    ) {
        FasstoGoodsDetailQuery query = FasstoGoodsDetailQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .godNm(godNm)
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
        if (FormatValidator.hasNoValue(godNm)) {
            throw new IllegalArgumentException("godNm is required.");
        }
    }
}
