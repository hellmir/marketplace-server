package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSettlementDailyCostsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSettlementDailyCostsResult;

public interface GetFasstoSettlementDailyCostsUseCase {
    /**
     * @param command 정산 일별 비용 조회 커맨드
     * @return 정산 일별 비용 조회 결과 {@link GetFasstoSettlementDailyCostsResult}
     * @Date 2026-02-08
     * @Author 성효빈
     * @Description 파스토 물류비 일별 비용을 조회합니다.
     */
    GetFasstoSettlementDailyCostsResult getDailyCosts(GetFasstoSettlementDailyCostsCommand command);
}
