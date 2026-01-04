package com.personal.marketnote.product.service.pricepolicy;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetPricePoliciesResult;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.GetPricePoliciesUseCase;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetPricePoliciesService implements GetPricePoliciesUseCase {
    private final FindPricePoliciesPort findPricePoliciesPort;

    @Override
    public GetPricePoliciesResult getPricePolicies(Long productId) {
        List<GetProductPricePolicyResult> items = findPricePoliciesPort.findByProductId(productId).stream()
                .map(pricePolicy -> new GetProductPricePolicyResult(
                        pricePolicy.id(),
                        pricePolicy.price(),
                        pricePolicy.discountPrice(),
                        pricePolicy.accumulatedPoint(),
                        pricePolicy.discountRate(),
                        pricePolicy.optionIds()
                ))
                .toList();

        return GetPricePoliciesResult.of(items);
    }
}


