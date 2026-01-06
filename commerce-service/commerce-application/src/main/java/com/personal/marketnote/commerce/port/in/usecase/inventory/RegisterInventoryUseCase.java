package com.personal.marketnote.commerce.port.in.usecase.inventory;

import com.personal.marketnote.commerce.port.in.command.inventory.RegisterInventoryCommand;

public interface RegisterInventoryUseCase {
    /**
     * @param command 재고 도메인 등록 커맨드
     * @Date 2026-01-06
     * @Author 성효빈
     * @Description 재고 도메인을 등록합니다.
     */
    void registerInventory(RegisterInventoryCommand command);
}
