package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.in.result.GetProductsResult;

public interface GetProductUseCase {
    /**
     * @param id 상품 ID
     * @return 상품 도메인 {@link Product}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 상품을 조회합니다.
     */
    Product getProduct(Long id);

    GetProductsResult getProducts(Long categoryId);
}
