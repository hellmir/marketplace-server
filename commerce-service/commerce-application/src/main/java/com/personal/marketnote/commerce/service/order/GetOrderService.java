package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.exception.OrderNotFoundException;
import com.personal.marketnote.commerce.exception.OrderProductNotFoundException;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryQuery;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderCountResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;
import com.personal.marketnote.commerce.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.commerce.port.out.order.FindOrderPort;
import com.personal.marketnote.commerce.port.out.order.FindOrderProductPort;
import com.personal.marketnote.commerce.port.out.product.FindProductByPricePolicyPort;
import com.personal.marketnote.commerce.port.out.result.product.ProductInfoResult;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDate.now;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetOrderService implements GetOrderUseCase {
    private final FindOrderPort findOrderPort;
    private final FindOrderProductPort findOrderProductPort;
    private final FindProductByPricePolicyPort findProductByPricePolicyPort;

    @Override
    public GetOrderResult getOrderAndOrderProducts(Long id) {
        Order order = getOrder(id);
        Map<Long, ProductInfoResult> orderedProductsByPricePolicyId = Optional.ofNullable(
                        findProductByPricePolicyPort.findByPricePolicyIds(
                                order.getOrderProducts()
                                        .stream()
                                        .map(OrderProduct::getPricePolicyId)
                                        .toList()
                        )
                )
                .orElse(Map.of());

        return GetOrderResult.from(order, orderedProductsByPricePolicyId);
    }

    @Override
    public Order getOrder(Long id) {
        return findOrderPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public GetOrdersResult getBuyerOrderHistory(GetBuyerOrderHistoryQuery query) {
        BuyerOrders buyerOrders = findBuyerOrders(query, true);

        if (!FormatValidator.hasValue(buyerOrders.orders())) {
            return GetOrdersResult.of(List.of(), Map.of());
        }

        return GetOrdersResult.of(
                buyerOrders.orders(),
                buyerOrders.orderedProductsByPricePolicyId()
        );
    }

    @Override
    public GetOrderCountResult getBuyerOrderCount(GetBuyerOrderHistoryQuery query) {
        BuyerOrders buyerOrders = findBuyerOrders(query, false);

        return GetOrderCountResult.of(buyerOrders.orders().size());
    }

    @Override
    public OrderProduct getOrderProduct(Long orderId, Long pricePolicyId) {
        return findOrderProductPort.findByOrderIdAndPricePolicyId(orderId, pricePolicyId)
                .orElseThrow(() -> new OrderProductNotFoundException(orderId, pricePolicyId));
    }

    private BuyerOrders findBuyerOrders(GetBuyerOrderHistoryQuery query, boolean includeProductDetails) {
        LocalDate today = now();
        List<Order> orders = findOrderPort.findByBuyerId(
                query.buyerId(),
                query.calculateStartDate(today),
                query.calculateEndDate(today),
                query.resolveStatuses()
        );

        if (!FormatValidator.hasValue(orders)) {
            return BuyerOrders.empty();
        }

        String productNameKeyword = query.resolvedProductName();
        boolean needProductDetails = includeProductDetails || FormatValidator.hasValue(productNameKeyword);

        Map<Long, ProductInfoResult> orderedProductsByPricePolicyId = needProductDetails
                ? findOrderedProductsByPricePolicyId(orders)
                : Map.of();

        List<Order> filteredOrders = filterOrdersByProductName(
                orders,
                orderedProductsByPricePolicyId,
                productNameKeyword
        );

        return BuyerOrders.of(filteredOrders, orderedProductsByPricePolicyId);
    }

    private Map<Long, ProductInfoResult> findOrderedProductsByPricePolicyId(List<Order> orders) {
        List<Long> pricePolicyIds = orders.stream()
                .flatMap(order -> order.getOrderProducts().stream())
                .map(OrderProduct::getPricePolicyId)
                .filter(FormatValidator::hasValue)
                .distinct()
                .toList();

        if (!FormatValidator.hasValue(pricePolicyIds)) {
            return Map.of();
        }

        return Optional.ofNullable(findProductByPricePolicyPort.findByPricePolicyIds(pricePolicyIds))
                .orElse(Map.of());
    }

    private List<Order> filterOrdersByProductName(
            List<Order> orders,
            Map<Long, ProductInfoResult> orderedProductsByPricePolicyId,
            String productNameKeyword
    ) {
        if (!FormatValidator.hasValue(productNameKeyword)) {
            return orders;
        }

        if (!FormatValidator.hasValue(orderedProductsByPricePolicyId)) {
            return List.of();
        }

        String keyword = productNameKeyword.toLowerCase();

        return orders.stream()
                .filter(order -> order.getOrderProducts().stream()
                        .anyMatch(orderProduct -> {
                            ProductInfoResult productInfo
                                    = orderedProductsByPricePolicyId.get(orderProduct.getPricePolicyId());

                            return FormatValidator.hasValue(productInfo)
                                    && FormatValidator.hasValue(productInfo.name())
                                    && FormatValidator.containsKeyword(productInfo.name(), keyword);
                        })
                )
                .toList();
    }

    private record BuyerOrders(
            List<Order> orders,
            Map<Long, ProductInfoResult> orderedProductsByPricePolicyId
    ) {
        private static BuyerOrders empty() {
            return new BuyerOrders(List.of(), Map.of());
        }

        private static BuyerOrders of(
                List<Order> orders,
                Map<Long, ProductInfoResult> orderedProductsByPricePolicyId
        ) {
            List<Order> resolvedOrders = FormatValidator.hasValue(orders)
                    ? orders
                    : List.of();
            Map<Long, ProductInfoResult> resolvedProducts = FormatValidator.hasValue(orderedProductsByPricePolicyId)
                    ? orderedProductsByPricePolicyId
                    : Map.of();

            return new BuyerOrders(resolvedOrders, resolvedProducts);
        }
    }
}
