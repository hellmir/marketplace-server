package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderStatusHistory;

public interface UpdateOrderPort {
    void update(Order order, OrderStatusHistory orderStatusHistory);
}

