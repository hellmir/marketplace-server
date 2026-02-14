package com.personal.marketnote.product.port.in.usecase.pricepolicy;

public interface DeletePricePolicyUseCase {
    /**
     * 가격 정책 삭제
     *
     * @param userId        사용자 ID
     * @param isAdmin       관리자 여부
     * @param productId     상품 ID
     * @param pricePolicyId 가격 정책 ID
     */
    void delete(Long userId, boolean isAdmin, Long productId, Long pricePolicyId);
}
