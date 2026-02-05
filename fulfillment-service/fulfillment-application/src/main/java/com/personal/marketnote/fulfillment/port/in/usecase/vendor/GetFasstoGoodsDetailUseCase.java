package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoGoodsDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;

public interface GetFasstoGoodsDetailUseCase {
    /**
     * @param command 상품 조회 커맨드
     * @return 상품 조회 결과 {@link GetFasstoGoodsResult}
     * @Date 2026-02-05
     * @Author 성효빈
     * @Description 파스토 상품을 조회합니다.
     */
    GetFasstoGoodsResult getGoodsDetail(GetFasstoGoodsDetailCommand command);
}
