package com.personal.marketnote.product.service.cart;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.exception.CartProductNotFoundException;
import com.personal.marketnote.product.port.in.usecase.cart.GetCartProductUseCase;
import com.personal.marketnote.product.port.out.cart.FindCartProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetCartProductService implements GetCartProductUseCase {
    private final FindCartProductPort findCartProductPort;

    @Override
    public CartProduct getCartProduct(Long userId, Long pricePolicyId) {
        return findCartProductPort.findCartProductByUserIdAndPricePolicyId(userId, pricePolicyId)
                .orElseThrow(() -> new CartProductNotFoundException(pricePolicyId));
    }

    @Override
    public boolean existsByUserIdAndPolicyId(Long userId, Long policyId) {
        return findCartProductPort.existsByUserIdAndPolicyId(userId, policyId);
    }
}
