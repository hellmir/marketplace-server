package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;

import java.util.List;

public interface FindProductsPort {
    List<Product> findAllActive();

    List<Product> findAllActiveByCategoryId(Long categoryId);
}


