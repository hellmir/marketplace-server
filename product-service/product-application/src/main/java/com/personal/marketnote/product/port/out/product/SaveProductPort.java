package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;

public interface SaveProductPort {
    Product save(Product product);
}


