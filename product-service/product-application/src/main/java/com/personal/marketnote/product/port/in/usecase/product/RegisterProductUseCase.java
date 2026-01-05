package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.result.product.RegisterProductResult;

public interface RegisterProductUseCase {
    /**
     * @param command 상품 등록 커맨드
     * @return 상품 등록 결과 {@link RegisterProductResult}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 상품을 등록합니다.
     */
    RegisterProductResult registerProduct(RegisterProductCommand command);
}
