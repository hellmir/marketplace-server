package com.personal.marketnote.commerce.port.in.usecase.order;

import com.personal.marketnote.commerce.port.in.command.order.UpdateOrderProductReviewStatusCommand;

public interface UpdateOrderProductUseCase {
    /**
     * @param command 리뷰 작성 여부 업데이트 커맨드
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 주문 상품의 리뷰 작성 여부를 업데이트합니다.
     */
    void updateReviewStatus(UpdateOrderProductReviewStatusCommand command);
}
