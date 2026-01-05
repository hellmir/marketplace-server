package com.personal.marketnote.order.port.out.order;

import com.personal.marketnote.product.domain.order.Order;

public interface UpdateOrderPort {
    void update(Order order);
}

