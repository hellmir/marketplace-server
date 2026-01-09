package com.personal.marketnote.commerce.port.out.product;

import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;

import java.util.List;
import java.util.Map;

public interface FindProductByPricePolicyPort {
    Map<Long, GetOrderedProductResult> findByPricePolicyIds(List<Long> pricePolicyIds);
}
