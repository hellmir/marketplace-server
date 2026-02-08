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
    private String inWay;
    private String ordNo;
    private String wrkStat;

    public static FasstoWarehousingQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate
    ) {
        return FasstoWarehousingQuery.of(customerCode, accessToken, startDate, endDate, null, null, null);
    }

    public static FasstoWarehousingQuery of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String inWay,
            String ordNo,
            String wrkStat
    ) {
        FasstoWarehousingQuery query = FasstoWarehousingQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .startDate(startDate)
                .endDate(endDate)
                .inWay(inWay)
                .ordNo(ordNo)
                .wrkStat(wrkStat)
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
