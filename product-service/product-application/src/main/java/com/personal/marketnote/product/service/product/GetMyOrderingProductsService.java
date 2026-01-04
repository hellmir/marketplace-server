package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.in.command.GetMyOrderingProductsCommand;
import com.personal.marketnote.product.port.in.command.OrderingItemCommand;
import com.personal.marketnote.product.port.in.result.cart.GetCartProductResult;
import com.personal.marketnote.product.port.in.result.product.GetMyOrderProductsResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.GetPricePoliciesUseCase;
import com.personal.marketnote.product.port.in.usecase.product.GetMyOrderingProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetMyOrderingProductsService implements GetMyOrderingProductsUseCase {
    private final GetPricePoliciesUseCase getPricePoliciesUseCase;

    @Override
    public GetMyOrderProductsResult getMyOrderingProducts(
            GetMyOrderingProductsCommand getMyOrderingProductsCommand
    ) {
        List<PricePolicy> pricePolicies = getPricePoliciesUseCase.getPricePolicies(
                getMyOrderingProductsCommand.orderingItemCommands()
                        .stream()
                        .map(OrderingItemCommand::pricePolicyId)
                        .toList()
        );

        List<GetCartProductResult> orderingProducts = new ArrayList<>();
        pricePolicies.forEach(
                pricePolicy -> getMyOrderingProductsCommand.orderingItemCommands()
                        .stream()
                        .filter(
                                request -> FormatValidator.equals(
                                        request.pricePolicyId(), pricePolicy.getId()
                                )
                        )
                        .findFirst()
                        .ifPresent(
                                command -> orderingProducts.add(
                                        GetCartProductResult.from(
                                                pricePolicy, command.imageUrl(), command.quantity()
                                        )
                                )
                        )
        );

        return GetMyOrderProductsResult.from(orderingProducts);
    }
}
