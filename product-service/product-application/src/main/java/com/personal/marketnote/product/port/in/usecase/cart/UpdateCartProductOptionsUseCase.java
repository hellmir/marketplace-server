package com.personal.marketnote.product.port.in.usecase.cart;

import com.personal.marketnote.product.port.in.command.UpdateCartProductOptionCommand;

public interface UpdateCartProductOptionsUseCase {
    /**
     * @param command 장바구니 상품 옵션 변경 커맨드
     * @Date 2026-01-04
     * @Author 성효빈
     * @Description 장바구니 상품 옵션을 변경합니다.
     */
    void updateCartProductOptions(UpdateCartProductOptionCommand command);
}
