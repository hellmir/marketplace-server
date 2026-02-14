package com.personal.marketnote.product.port.in.usecase.option;

import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.option.UpdateProductOptionsResult;

public interface RegisterProductOptionsUseCase {
    /**
     * 상품 옵션 등록
     *
     * @param userId  사용자 ID
     * @param isAdmin 관리자 여부
     * @param command 상품 옵션 등록 커맨드
     * @return 상품 옵션 등록 결과 {@link UpdateProductOptionsResult}
     */
    UpdateProductOptionsResult registerProductOptions(
            Long userId, boolean isAdmin, RegisterProductOptionsCommand command
    );
}
