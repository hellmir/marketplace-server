package com.personal.marketnote.product.service.pricepolicy;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetPricePoliciesResult;
import com.personal.marketnote.product.port.in.result.pricepolicy.PricePolicyItemResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.GetPricePoliciesUseCase;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetPricePoliciesService implements GetPricePoliciesUseCase {
    private final FindPricePoliciesPort findPricePoliciesPort;

    @Override
    public GetPricePoliciesResult getPricePolicies(Long productId) {
        var items = findPricePoliciesPort.findByProductId(productId).stream()
                .map(pp -> new PricePolicyItemResult(
                        pp.id(),
                        pp.price(),
                        pp.discountPrice(),
                        pp.accumulatedPoint(),
                        pp.discountRate(),
                        pp.optionIds() == null || pp.optionIds().isEmpty(),
                        pp.optionIds()
                ))
                .collect(Collectors.toList());
        return GetPricePoliciesResult.of(items);
    }
}


