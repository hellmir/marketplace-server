package com.personal.marketnote.product.port.in.usecase.cart;

import com.personal.marketnote.product.port.in.command.DeleteCartProductCommand;

public interface DeleteCartProductUseCase {
    /**
     * @param command 장바구니 상품 삭제 커맨드
     * @Date 2026-01-04
     * @Author 성효빈
     * @Description 장바구니 상품을 삭제합니다.
     */
    void deleteCartProduct(DeleteCartProductCommand command);
}
