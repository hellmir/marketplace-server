package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.in.result.GetProductInfoWithOptionsResult;
import com.personal.marketnote.product.port.in.result.GetProductsResult;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GetProductUseCase {
    /**
     * @param id 상품 ID
     * @return 상품 도메인 {@link Product}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 상품을 조회합니다.
     */
    Product getProduct(Long id);

    /**
     * @param id             상품 ID
     * @param optionContents 선택할 옵션명 목록 (선택)
     * @return 상품 상세 정보 + 옵션 카테고리/옵션(선택 상태 포함)
     */
    GetProductInfoWithOptionsResult getProductInfo(Long id, List<String> optionContents);

    /**
     * @param categoryId    카테고리 ID
     * @param cursor        커서
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 상품 목록 조회 결과 {@link GetProductsResult}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 상품 목록을 조회합니다.
     */
    GetProductsResult getProducts(
            Long categoryId,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );
}
