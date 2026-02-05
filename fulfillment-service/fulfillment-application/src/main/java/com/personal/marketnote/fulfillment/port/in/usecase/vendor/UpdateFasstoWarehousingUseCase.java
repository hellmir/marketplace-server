package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehousingResult;

public interface UpdateFasstoWarehousingUseCase {
    /**
     * @param command 입고 수정 요청 커맨드
     * @return 입고 수정 결과 {@link UpdateFasstoWarehousingResult}
     * @Date 2026-02-05
     * @Author 성효빈
     * @Description 파스토 상품 입고 요청을 수정합니다.
     */
    UpdateFasstoWarehousingResult updateWarehousing(UpdateFasstoWarehousingCommand command);
}
