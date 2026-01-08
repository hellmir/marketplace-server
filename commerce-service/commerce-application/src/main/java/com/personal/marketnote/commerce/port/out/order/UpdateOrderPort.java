package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderHistory;

public interface UpdateOrderPort {
    void update(Order order, OrderHistory orderHistory);
}

