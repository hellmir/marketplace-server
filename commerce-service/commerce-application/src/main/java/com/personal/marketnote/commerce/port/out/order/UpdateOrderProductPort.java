package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.OrderProduct;

public interface UpdateOrderProductPort {
    void update(OrderProduct orderProduct);
}

