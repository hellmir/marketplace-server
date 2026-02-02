package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;

public interface RegisterFasstoWarehousingUseCase {
    /**
     * @param command 입고 요청 커맨드
     * @return 입고 요청 결과 {@link RegisterFasstoWarehousingResult}
     * @Date 2026-01-31
     * @Author 성효빈
     * @Description 파스토 상품 입고 요청을 등록합니다.
     */
    RegisterFasstoWarehousingResult registerWarehousing(RegisterFasstoWarehousingCommand command);
}
