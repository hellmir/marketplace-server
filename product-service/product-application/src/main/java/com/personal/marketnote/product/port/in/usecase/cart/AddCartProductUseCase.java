package com.personal.marketnote.product.port.in.usecase.cart;

import com.personal.marketnote.product.port.in.command.AddCartProductCommand;

public interface AddCartProductUseCase {
    /**
     * @param command 장바구니 추가 커맨드
     * @Date 2026-01-04
     * @Author 성효빈
     * @Description 장바구니를 추가합니다.
     */
    void addCartProduct(AddCartProductCommand command);
}
