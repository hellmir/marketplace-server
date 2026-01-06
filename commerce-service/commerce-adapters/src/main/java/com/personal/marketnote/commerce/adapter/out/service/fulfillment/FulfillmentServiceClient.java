package com.personal.marketnote.commerce.adapter.out.service.fulfillment;

import com.personal.marketnote.commerce.port.in.usecase.inventory.ReduceProductStockUseCase;
import com.personal.marketnote.product.domain.order.OrderProduct;

import java.util.List;

public class FulfillmentServiceClient implements ReduceProductStockUseCase {
    @Override
    public void reduce(List<OrderProduct> orderProducts) {

    }
}
