package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.out.result.product.ProductInfoResult;

import java.util.List;
import java.util.Map;

public record GetOrdersResult(
        List<Order> orders,
        Map<Long, ProductInfoResult> orderedProducts
) {
    public static GetOrdersResult of(List<Order> orders, Map<Long, ProductInfoResult> productInfoResultsByPricePolicyId) {
        return new GetOrdersResult(orders, productInfoResultsByPricePolicyId);
    }
}
