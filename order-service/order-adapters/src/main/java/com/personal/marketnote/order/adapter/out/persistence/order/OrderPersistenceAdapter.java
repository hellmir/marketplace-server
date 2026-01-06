package com.personal.marketnote.order.adapter.out.persistence.order;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.order.adapter.out.mapper.OrderJpaEntityToDomainMapper;
import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderJpaEntity;
import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import com.personal.marketnote.order.adapter.out.persistence.order.repository.OrderJpaRepository;
import com.personal.marketnote.order.exception.OrderNotFoundException;
import com.personal.marketnote.order.port.out.order.FindOrderPort;
import com.personal.marketnote.order.port.out.order.SaveOrderPort;
import com.personal.marketnote.order.port.out.order.UpdateOrderPort;
import com.personal.marketnote.product.domain.order.Order;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements SaveOrderPort, FindOrderPort, UpdateOrderPort {
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
