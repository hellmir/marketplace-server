package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.Order;

public interface SaveOrderPort {
    Order save(Order order);
}

