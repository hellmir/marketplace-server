package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingResult;

public interface GetFasstoWarehousingUseCase {
    /**
     * @param command 입고 목록 조회 커맨드
     * @return 입고 목록 조회 결과 {@link GetFasstoWarehousingResult}
     * @Date 2026-01-31
     * @Author 성효빈
     * @Description 파스토 입고 목록을 조회합니다.
     */
    GetFasstoWarehousingResult getWarehousing(GetFasstoWarehousingCommand command);
}
