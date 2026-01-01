package com.personal.marketnote.product.port.out.productoption;

import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.List;

public interface FindProductOptionCategoryPort {
    /**
     * @param productId 상품 ID
     * @return 활성 옵션 카테고리 및 하위 옵션 목록
     */
    List<ProductOptionCategory> findActiveWithOptionsByProductId(Long productId);
}


