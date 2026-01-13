package com.personal.marketnote.community.port.out.product;

import com.personal.marketnote.community.port.out.result.product.ProductInfoResult;

import java.util.List;
import java.util.Map;

public interface FindProductByPricePolicyPort {
    Map<Long, ProductInfoResult> findByPricePolicyIds(List<Long> pricePolicyIds);
}
