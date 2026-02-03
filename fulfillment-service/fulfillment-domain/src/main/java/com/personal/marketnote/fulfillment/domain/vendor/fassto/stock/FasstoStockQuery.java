package com.personal.marketnote.fulfillment.domain.vendor.fassto.stock;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoStockQuery {
    private String customerCode;
    private String accessToken;
    private String outOfStockYn;

    public static FasstoStockQuery of(
            String customerCode,
            String accessToken,
            String outOfStockYn
    ) {
        FasstoStockQuery query = FasstoStockQuery.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .outOfStockYn(outOfStockYn)
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
