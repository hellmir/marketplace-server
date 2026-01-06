package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.product.domain.order.Order;

public interface SaveOrderPort {
    Order save(Order order);
}

