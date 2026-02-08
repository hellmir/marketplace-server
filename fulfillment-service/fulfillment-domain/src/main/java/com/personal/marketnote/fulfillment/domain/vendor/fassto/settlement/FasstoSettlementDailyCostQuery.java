package com.personal.marketnote.fulfillment.domain.vendor.fassto.settlement;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoSettlementDailyCostQuery {
    private String yearMonth;
    private String whCd;
    private String customerCode;
    private String accessToken;

    public static FasstoSettlementDailyCostQuery of(
            String yearMonth,
            String whCd,
            String customerCode,
            String accessToken
    ) {
        FasstoSettlementDailyCostQuery query = FasstoSettlementDailyCostQuery.builder()
                .yearMonth(yearMonth)
                .whCd(whCd)
                .customerCode(customerCode)
                .accessToken(accessToken)
                .build();
        query.validate();
        return query;
    }

    private void validate() {
        if (FormatValidator.hasNoValue(yearMonth)) {
            throw new IllegalArgumentException("yearMonth is required.");
        }
        if (FormatValidator.hasNoValue(whCd)) {
            throw new IllegalArgumentException("whCd is required.");
        }
        if (FormatValidator.hasNoValue(customerCode)) {
            throw new IllegalArgumentException("customerCode is required.");
        }
        if (FormatValidator.hasNoValue(accessToken)) {
            throw new IllegalArgumentException("accessToken is required.");
        }
    }
}
