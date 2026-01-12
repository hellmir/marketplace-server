package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.OrderProduct;

import java.util.Optional;

public interface FindOrderProductPort {
    Optional<OrderProduct> findByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId);
}
