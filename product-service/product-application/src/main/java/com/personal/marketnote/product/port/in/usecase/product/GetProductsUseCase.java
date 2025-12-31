package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.result.GetProductsResult;

public interface GetProductsUseCase {
    GetProductsResult getProducts(Long categoryId);
}


