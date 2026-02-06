package com.personal.marketnote.commerce.port.in.usecase.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.List;
import java.util.Set;

public interface GetInventoryUseCase {
    /**
     * @param pricePolicyIds 가격 정책 ID 목록
     * @return 상품 재고 목록 {@link Set<Inventory>}
     * @Date 2026-01-07
     * @Author 성효빈
     * @Description 상품 재고 목록을 조회합니다.
     */
    Set<Inventory> getInventories(List<Long> pricePolicyIds);

    /**
     * @param pricePolicyId 가격 정책 ID
     * @return 해당 가격 정책에 대한 재고가 존재하는지 여부
     * @Date 2026-02-07
     * @Author 성효빈
     * @Description 해당 가격 정책에 대한 재고가 존재하는지 여부를 조회합니다.
     */
    boolean existsInventory(Long pricePolicyId);
}
