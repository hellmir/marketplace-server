package com.personal.marketnote.product.port.out.productcategory;

import java.util.List;

public interface ReplaceProductCategoriesPort {
    void replaceProductCategories(Long productId, List<Long> categoryIds);
}
