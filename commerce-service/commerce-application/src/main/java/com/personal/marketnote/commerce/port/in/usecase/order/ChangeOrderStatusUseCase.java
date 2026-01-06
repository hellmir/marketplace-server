package com.personal.marketnote.commerce.port.in.usecase.order;

import com.personal.marketnote.commerce.port.in.command.ChangeOrderStatusCommand;

public interface ChangeOrderStatusUseCase {
    /**
     * @param command 주문 상태 변경 커맨드
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 주문 상태를 변경합니다.
     */
    void changeOrderStatus(ChangeOrderStatusCommand command);
}
