package com.personal.marketnote.product.port.in.usecase.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;

public interface GetCartProductUseCase {
    /**
     * @param userId        회원 ID
     * @param pricePolicyId 가격 정책 ID
     * @return 장바구니 상품 {@link CartProduct}
     */
    CartProduct getCartProduct(Long userId, Long pricePolicyId);

    boolean existsByUserIdAndPolicyId(Long userId, Long policyId);
}
