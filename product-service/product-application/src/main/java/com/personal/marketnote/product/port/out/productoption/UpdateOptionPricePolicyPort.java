package com.personal.marketnote.product.port.out.productoption;

import java.util.List;

public interface UpdateOptionPricePolicyPort {
    void assignPricePolicyToOptions(Long pricePolicyId, List<Long> optionIds);
}


