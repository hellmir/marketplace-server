package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;

public interface GetFasstoStockDetailUseCase {
    /**
     * @param command 단일 상품 재고 정보 조회 커맨드
     * @return 단일 상품 재고 정보 조회 결과 {@link GetFasstoStocksResult}
     * @Date 2026-02-05
     * @Author 성효빈
     * @Description 파스토 단일 상품 재고 정보를 조회합니다.
     */
    GetFasstoStocksResult getStockDetail(GetFasstoStockDetailCommand command);
}
