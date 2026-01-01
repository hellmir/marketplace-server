package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FindProductPort {
    /**
     * @param productId 상품 ID
     * @param sellerId  판매자 ID
     * @return 상품 존재 여부 {@link boolean}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 상품의 존재 여부를 확인합니다.
     */
    boolean existsByIdAndSellerId(Long productId, Long sellerId);

    /**
     * @param productId 상품 ID
     * @return 상품 도메인 {@link Product}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 활성화/비활성화/비노출 상품을 조회합니다.
     */
    Optional<Product> findById(Long productId);

    /**
     * @return 모든 상품 목록 {@link List<Product>}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 모든 활성화/비활성화/비노출 상품 목록을 조회합니다.
     */
    List<Product> findAll();

    /**
     * @param categoryId 카테고리 ID
     * @return 카테고리에 속한 모든 상품 목록 {@link List<Product>}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 카테고리에 속한 모든 상품 목록을 조회합니다.
     */
    List<Product> findAllByCategoryId(Long categoryId);

    /**
     * @param cursor   커서
     * @param pageable 페이지네이션 정보
     * @return 모든 상품 목록 {@link List<Product>}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 모든 상품 목록을 조회합니다(페이지네이션 적용).
     */
    List<Product> findAllActive(
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );

    /**
     * @param categoryId 카테고리 ID
     * @param cursor     커서
     * @param pageable   페이지네이션 정보
     * @return 카테고리에 속한 상품 목록 {@link List<Product>}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 카테고리에 속한 상품 목록을 조회합니다(페이지네이션 적용).
     */
    List<Product> findAllActiveByCategoryId(
            Long categoryId,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );

    /**
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 상품 총 개수 {@link long}
     * @Date 2026-01-01
     * @Author 성효빈
     * @Description 상품 총 개수를 조회합니다.
     */
    long countActive(ProductSearchTarget searchTarget, String searchKeyword);

    /**
     * @param categoryId    카테고리 ID
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 카테고리 상품 총 개수 {@link long}
     * @Date 2026-01-01
     * @Author 성효빈
     * @Description 카테고리 상품 총 개수를 조회합니다.
     */
    long countActiveByCategoryId(Long categoryId, ProductSearchTarget searchTarget, String searchKeyword);
}

