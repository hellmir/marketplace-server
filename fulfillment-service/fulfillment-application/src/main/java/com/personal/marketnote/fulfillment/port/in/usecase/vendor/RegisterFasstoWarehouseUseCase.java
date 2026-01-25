package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehouseCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;

public interface RegisterFasstoWarehouseUseCase {
    /**
     * @param command 출고처 등록 커맨드
     * @return 출고처 등록 결과 {@link RegisterFasstoWarehouseResult}
     * @Date 2026-01-25
     * @Author 성효빈
     * @Description 파스토 출고처를 등록합니다.
     */
    RegisterFasstoWarehouseResult registerWarehouse(RegisterFasstoWarehouseCommand command);
}
