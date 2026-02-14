package com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoWarehousingAbnormalQuery {
    private String customerCode;
    private String accessToken;
    private String whCd;
    private String slipNo;

    public static FasstoWarehousingAbnormalQuery of(
            String customerCode,
            String accessToken,
            String whCd,
            String slipNo
    ) {
        FasstoWarehousingAbnormalQuery query = FasstoWarehousingAbnormalQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .whCd(whCd)
                .slipNo(slipNo)
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
        if (FormatValidator.hasNoValue(whCd)) {
            throw new IllegalArgumentException("whCd is required.");
        }
        if (FormatValidator.hasNoValue(slipNo)) {
            throw new IllegalArgumentException("slipNo is required.");
        }
    }
}
