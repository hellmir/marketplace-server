package com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoWarehousingDetailQuery {
    private String customerCode;
    private String accessToken;
    private String slipNo;
    private String ordNo;

    public static FasstoWarehousingDetailQuery of(
            String customerCode,
            String accessToken,
            String slipNo,
            String ordNo
    ) {
        FasstoWarehousingDetailQuery query = FasstoWarehousingDetailQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .slipNo(slipNo)
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
        if (FormatValidator.hasNoValue(slipNo)) {
            throw new IllegalArgumentException("slipNo is required.");
        }
    }
}
