package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindPricePoliciesPort {
    List<PricePolicy> findByProductId(Long productId);

    List<PricePolicy> findByIds(List<Long> ids);

    List<PricePolicy> findPricePolicies(
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );

    List<PricePolicy> findPricePoliciesByCategoryId(
            Long categoryId,
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );

    /**
     * @param categoryId    카테고리 ID
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 카테고리 가격 정책 총 개수 {@link long}
     * @Date 2026-01-01
     * @Author 성효빈
     * @Description 카테고리 가격 정책 총 개수를 조회합니다.
     */
    long countActivePricePoliciesByCategoryId(Long categoryId, ProductSearchTarget searchTarget, String searchKeyword);
}
