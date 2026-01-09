package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.exception.OrderNotFoundException;
import com.personal.marketnote.commerce.port.in.command.order.GetOrdersQuery;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersDomainResult;
import com.personal.marketnote.commerce.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.commerce.port.out.order.FindOrderPort;
import com.personal.marketnote.commerce.port.out.product.FindProductByPricePolicyPort;
import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;
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
    private final FindProductByPricePolicyPort findProductByPricePolicyPort;

    @Override
    public Order getOrder(Long id) {
        return findOrderPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public GetOrdersDomainResult getOrders(GetOrdersQuery query) {
        List<Order> orders = findOrderPort.findByBuyerId(
                query.buyerId(),
                query.calculateStartDate(now()),
                query.calculateEndDate(now()),
                query.resolveStatuses()
        );

        if (!FormatValidator.hasValue(orders)) {
            return GetOrdersDomainResult.of(List.of(), Map.of());
        }

        List<Long> pricePolicyIds = orders.stream()
                .flatMap(order -> order.getOrderProducts().stream())
                .map(orderProduct -> orderProduct.getPricePolicyId())
                .filter(FormatValidator::hasValue)
                .distinct()
                .toList();

        Map<Long, GetOrderedProductResult> productSummaryMap =
                Optional.ofNullable(findProductByPricePolicyPort.findByPricePolicyIds(pricePolicyIds))
                        .orElse(Map.of());

        String productNameKeyword = query.resolvedProductName();
        if (FormatValidator.hasValue(productNameKeyword)) {

            String keyword = productNameKeyword.toLowerCase();

            orders = orders.stream()
                    .filter(order -> order.getOrderProducts().stream()
                            .anyMatch(orderProduct -> {
                                GetOrderedProductResult summary =
                                        productSummaryMap.get(orderProduct.getPricePolicyId());

                                return summary != null
                                        && summary.name() != null
                                        && summary.name().toLowerCase().contains(keyword);
                            })
                    )
                    .toList();
        }

        return GetOrdersDomainResult.of(orders, productSummaryMap);
    }
}
