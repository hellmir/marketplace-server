package com.personal.marketnote.order.port.out.order;

import com.personal.marketnote.product.domain.order.Order;

import java.util.Optional;

public interface FindOrderPort {
    Optional<Order> findById(Long id);
}

