package com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoDeliveryStatusQuery {
    private String customerCode;
    private String accessToken;
    private String startDate;
    private String endDate;
    private String outDiv;

    public static FasstoDeliveryStatusQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String outDiv
    ) {
        FasstoDeliveryStatusQuery query = FasstoDeliveryStatusQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .startDate(startDate)
                .endDate(endDate)
                .outDiv(outDiv)
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
        if (FormatValidator.hasNoValue(outDiv)) {
            throw new IllegalArgumentException("outDiv is required.");
        }
    }
}
