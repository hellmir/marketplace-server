package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.exception.OrderProductNotFoundException;

public interface UpdateOrderProductPort {
    void update(OrderProduct orderProduct) throws OrderProductNotFoundException;
}

