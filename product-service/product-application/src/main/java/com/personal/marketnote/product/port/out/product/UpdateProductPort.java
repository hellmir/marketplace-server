package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.exception.ProductNotFoundException;

public interface UpdateProductPort {
    void update(Product product) throws ProductNotFoundException;
}
