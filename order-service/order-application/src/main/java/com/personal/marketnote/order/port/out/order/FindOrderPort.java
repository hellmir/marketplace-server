package com.personal.marketnote.order.port.out.order;

import com.personal.marketnote.product.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface FindOrderPort {
    Optional<Order> findById(Long id);

    List<Order> findByBuyerId(Long buyerId);
}

