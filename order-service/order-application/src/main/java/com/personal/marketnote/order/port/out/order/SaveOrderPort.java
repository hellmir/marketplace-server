package com.personal.marketnote.order.port.out.order;

import com.personal.marketnote.product.domain.order.Order;

public interface SaveOrderPort {
    Order save(Order order);
}

