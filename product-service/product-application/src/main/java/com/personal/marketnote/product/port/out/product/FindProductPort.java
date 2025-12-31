package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface FindProductPort {
    /**
     * @param productId 상품 ID
     * @param sellerId  판매자 ID
     * @return 상품 존재 여부
     */
    boolean existsByIdAndSellerId(Long productId, Long sellerId);

    /**
     * @param productId 상품 ID
     * @return 상품 존재 여부
     */
    Optional<Product> findById(Long productId);

    /**
     * @return 모든 상품 목록
     */
    List<Product> findAll();

    /**
     * @return 모든 활성 상품 목록
     */
    List<Product> findAllActive();

    /**
     * @param categoryId 카테고리 ID
     * @return 카테고리에 속한 활성 상품 목록
     */
    List<Product> findAllByCategoryId(Long categoryId);

    /**
     * @param categoryId 카테고리 ID
     * @return 카테고리에 속한 활성 상품 목록
     */
    List<Product> findAllActiveByCategoryId(Long categoryId);
}
