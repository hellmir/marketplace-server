package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.Order;

public interface UpdateOrderPort {
    void update(Order order);
}

