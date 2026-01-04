package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;

import java.util.List;

public interface FindPricePoliciesPort {


    List<GetProductPricePolicyResult> findByProductId(Long productId);
}
