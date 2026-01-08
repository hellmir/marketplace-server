package com.personal.marketnote.commerce.adapter.out.persistence.order;

import com.personal.marketnote.commerce.adapter.out.mapper.OrderJpaEntityToDomainMapper;
import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderHistoryJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.order.repository.OrderHistoryJpaRepository;
import com.personal.marketnote.commerce.adapter.out.persistence.order.repository.OrderJpaRepository;
import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.exception.OrderNotFoundException;
import com.personal.marketnote.commerce.port.out.order.FindOrderPort;
import com.personal.marketnote.commerce.port.out.order.SaveOrderPort;
import com.personal.marketnote.commerce.port.out.order.UpdateOrderPort;
import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements SaveOrderPort, FindOrderPort, UpdateOrderPort {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderHistoryJpaRepository orderHistoryJpaRepository;

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

        orderHistoryJpaRepository.save(OrderHistoryJpaEntity.from(savedOrderEntity));

        return OrderJpaEntityToDomainMapper.mapToDomain(savedOrderEntity).orElse(null);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id)
                .flatMap(OrderJpaEntityToDomainMapper::mapToDomain);
    }

    @Override
    public List<Order> findByBuyerId(Long buyerId) {
        return orderJpaRepository.findByBuyerId(buyerId)
                .stream()
                .map(OrderJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public void update(Order order) throws OrderNotFoundException {
        OrderJpaEntity entity = findEntityById(order.getId());
        entity.updateFrom(order);
    }

    private OrderJpaEntity findEntityById(Long id) throws OrderNotFoundException {
        return orderJpaRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
