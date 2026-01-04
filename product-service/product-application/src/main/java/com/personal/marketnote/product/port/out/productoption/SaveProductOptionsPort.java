package com.personal.marketnote.product.port.out.productoption;

import com.personal.marketnote.product.domain.option.ProductOptionCategory;

public interface SaveProductOptionsPort {
    ProductOptionCategory save(ProductOptionCategory productOptionCategory);
}
