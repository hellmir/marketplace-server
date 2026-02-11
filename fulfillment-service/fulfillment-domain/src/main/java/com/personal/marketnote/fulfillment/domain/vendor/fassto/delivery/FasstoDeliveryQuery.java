package com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoDeliveryQuery {
    private String customerCode;
    private String accessToken;
    private String startDate;
    private String endDate;
    private String status;
    private String outDiv;
    private String ordNo;

    public static FasstoDeliveryQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String status,
            String outDiv
    ) {
        return FasstoDeliveryQuery.of(customerCode, accessToken, startDate, endDate, status, outDiv, null);
    }

    public static FasstoDeliveryQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String status,
            String outDiv,
            String ordNo
    ) {
        FasstoDeliveryQuery query = FasstoDeliveryQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .outDiv(outDiv)
                .ordNo(ordNo)
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
        if (FormatValidator.hasNoValue(status)) {
            throw new IllegalArgumentException("status is required.");
        }
        if (FormatValidator.hasNoValue(outDiv)) {
            throw new IllegalArgumentException("outDiv is required.");
        }
    }
}
