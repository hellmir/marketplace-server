package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.exception.OrderNotFoundException;
import com.personal.marketnote.commerce.exception.OrderProductNotFoundException;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryCommand;
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
        Map<Long, ProductInfoResult> orderedProducts = Optional.ofNullable(
                        findProductByPricePolicyPort.findByPricePolicyIds(
                                order.getOrderProducts()
                                        .stream()
                                        .map(OrderProduct::getPricePolicyId)
                                        .toList()
                        )
                )
                .orElse(Map.of());

        return GetOrderResult.from(order, orderedProducts);
    }

    @Override
    public Order getOrder(Long id) {
        return findOrderPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public GetOrdersResult getBuyerOrderHistory(GetBuyerOrderHistoryCommand command) {
        List<Order> orders = findOrderPort.findByBuyerId(
                command.buyerId(),
                command.calculateStartDate(now()),
                command.calculateEndDate(now()),
                command.resolveStatuses()
        );

        if (!FormatValidator.hasValue(orders)) {
            return GetOrdersResult.of(List.of(), Map.of());
        }

        List<Long> pricePolicyIds = orders.stream()
                .flatMap(order -> order.getOrderProducts().stream())
                .map(OrderProduct::getPricePolicyId)
                .filter(FormatValidator::hasValue)
                .distinct()
                .toList();

        Map<Long, ProductInfoResult> orderedProducts = findProductByPricePolicyPort.findByPricePolicyIds(pricePolicyIds);

        String productNameKeyword = command.resolvedProductName();
        if (FormatValidator.hasValue(productNameKeyword)) {
            String keyword = productNameKeyword.toLowerCase();

            orders = orders.stream()
                    .filter(order -> order.getOrderProducts().stream()
                            .anyMatch(orderProduct -> {
                                ProductInfoResult productInfo = orderedProducts.get(orderProduct.getPricePolicyId());

                                return FormatValidator.hasValue(productInfo)
                                        && FormatValidator.hasValue(productInfo.name())
                                        && FormatValidator.containsKeyword(productInfo.name(), keyword);
                            })
                    )
                    .toList();
        }

        return GetOrdersResult.of(orders, orderedProducts);
    }

    @Override
    public OrderProduct getOrderProduct(Long orderId, Long pricePolicyId) {
        return findOrderProductPort.findByOrderIdAndPricePolicyId(orderId, pricePolicyId)
                .orElseThrow(() -> new OrderProductNotFoundException(orderId, pricePolicyId));
    }
}
