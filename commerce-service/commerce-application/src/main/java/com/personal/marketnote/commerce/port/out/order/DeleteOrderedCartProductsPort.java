package com.personal.marketnote.commerce.port.out.order;

import java.util.List;

public interface DeleteOrderedCartProductsPort {
    void delete(List<Long> pricePolicyIds);
}

