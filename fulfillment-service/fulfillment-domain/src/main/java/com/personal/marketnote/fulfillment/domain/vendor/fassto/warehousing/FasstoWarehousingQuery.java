package com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoWarehousingQuery {
    private String customerCode;
    private String accessToken;
    private String startDate;
    private String endDate;

    public static FasstoWarehousingQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate
    ) {
        FasstoWarehousingQuery query = FasstoWarehousingQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .startDate(startDate)
                .endDate(endDate)
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
        if (FormatValidator.hasNoValue(startDate)) {
            throw new IllegalArgumentException("startDate is required.");
        }
        if (FormatValidator.hasNoValue(endDate)) {
            throw new IllegalArgumentException("endDate is required.");
        }
    }
}
