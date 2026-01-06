package com.personal.marketnote.order.port.out.order;

import java.util.List;

public interface DeleteOrderedCartProductsPort {
    void delete(List<Long> pricePolicyIds);
}

