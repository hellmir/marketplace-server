package com.personal.marketnote.product.port.out.inventory;

import com.personal.marketnote.common.domain.exception.illegalargument.numberformat.ParsingIntegerException;

public interface FindCacheStockPort {
    int findByPricePolicyId(Long pricePolicyId) throws ParsingIntegerException;
}
