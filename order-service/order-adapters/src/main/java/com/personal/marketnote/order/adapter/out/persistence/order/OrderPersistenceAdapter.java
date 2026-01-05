package com.personal.marketnote.order.adapter.out.persistence.order;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.order.adapter.out.mapper.OrderJpaEntityToDomainMapper;
import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderJpaEntity;
import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import com.personal.marketnote.order.adapter.out.persistence.order.repository.OrderJpaRepository;
import com.personal.marketnote.order.port.out.order.SaveOrderPort;
import com.personal.marketnote.product.domain.order.Order;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements SaveOrderPort {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderJpaEntity orderEntity = OrderJpaEntity.from(order);
        OrderJpaEntity savedOrderEntity = orderJpaRepository.save(orderEntity);

        if (FormatValidator.hasValue(order.getOrderProducts())) {
            order.getOrderProducts().forEach(orderProduct -> {
                OrderProductJpaEntity orderProductEntity = OrderProductJpaEntity.from(
                        orderProduct, savedOrderEntity
                );
                savedOrderEntity.addOrderProduct(orderProductEntity);
            });
        }

        OrderJpaEntity finalEntity = orderJpaRepository.save(savedOrderEntity);

        return OrderJpaEntityToDomainMapper.mapToDomain(finalEntity).orElse(null);
    }
}

