package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.SyncFasstoAllStockCommand;

public interface SyncFasstoAllStockUseCase {
    /**
     * @param command 전체 재고 동기화 커맨드
     * @Date 2026-02-07
     * @Author 성효빈
     * @Description 파스토 재고 목록을 기준으로 커머스 재고를 동기화합니다.
     */
    void syncAll(SyncFasstoAllStockCommand command);
}
