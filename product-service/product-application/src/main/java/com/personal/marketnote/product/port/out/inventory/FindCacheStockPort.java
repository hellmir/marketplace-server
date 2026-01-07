package com.personal.marketnote.product.port.out.inventory;

import com.personal.marketnote.common.domain.exception.illegalargument.numberformat.ParsingIntegerException;

import java.util.List;

public interface FindCacheStockPort {
    int findByPricePolicyId(Long pricePolicyId) throws ParsingIntegerException;

    List<Integer> findByPricePolicyIds(List<Long> pricePolicyIds);
}
