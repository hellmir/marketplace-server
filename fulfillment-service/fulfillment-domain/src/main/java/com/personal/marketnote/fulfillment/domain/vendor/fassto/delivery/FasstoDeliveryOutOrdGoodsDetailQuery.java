package com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoDeliveryOutOrdGoodsDetailQuery {
    private String customerCode;
    private String accessToken;
    private String outOrdSlipNo;

    public static FasstoDeliveryOutOrdGoodsDetailQuery of(
            String customerCode,
            String accessToken,
            String outOrdSlipNo
    ) {
        FasstoDeliveryOutOrdGoodsDetailQuery query = FasstoDeliveryOutOrdGoodsDetailQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .outOrdSlipNo(outOrdSlipNo)
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
        if (FormatValidator.hasNoValue(outOrdSlipNo)) {
            throw new IllegalArgumentException("outOrdSlipNo is required.");
        }
    }
}
