package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;

public interface GetFasstoWarehousesUseCase {
    /**
     * @param command 출고처 목록 조회 커맨드
     * @return 출고처 목록 조회 결과 {@link GetFasstoWarehousesResult}
     * @Date 2026-01-25
     * @Author 성효빈
     * @Description 파스토 출고처 목록을 조회합니다.
     */
    GetFasstoWarehousesResult getWarehouses(GetFasstoWarehousesCommand command);
}
