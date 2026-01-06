package com.personal.marketnote.commerce.service.inventory;

import com.personal.marketnote.commerce.port.in.usecase.inventory.ReduceProductStockUseCase;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.order.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ReduceProductStockService implements ReduceProductStockUseCase {
    @Override
    public void reduce(List<OrderProduct> orderProducts) {

    }
}
