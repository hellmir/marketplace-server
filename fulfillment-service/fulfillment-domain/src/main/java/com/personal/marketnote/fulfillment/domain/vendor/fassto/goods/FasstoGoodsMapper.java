package com.personal.marketnote.fulfillment.domain.vendor.fassto.goods;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FasstoGoodsMapper {
    private String customerCode;
    private String accessToken;
    private List<FasstoGoodsItemMapper> goods;

    public static FasstoGoodsMapper register(
            String customerCode,
            String accessToken,
            List<FasstoGoodsItemMapper> goods
    ) {
        FasstoGoodsMapper mapper = FasstoGoodsMapper.builder()
                .customerCode(customerCode)
                .accessToken(accessToken)
                .goods(goods)
                .build();
        mapper.validate();

        return mapper;
    }

    private void validate() {
        if (FormatValidator.hasNoValue(customerCode)) {
            throw new IllegalArgumentException("customerCode is required.");
        }
        if (FormatValidator.hasNoValue(accessToken)) {
            throw new IllegalArgumentException("accessToken is required.");
        }
        if (FormatValidator.hasNoValue(goods)) {
            throw new IllegalArgumentException("goods is required.");
        }
    }

    public List<Map<String, Object>> toPayload() {
        return goods.stream()
                .map(FasstoGoodsItemMapper::toPayload)
                .toList();
    }

    public String getProductId() {
        return goods.getFirst().getCstGodCd();
    }
}
