package com.personal.marketnote.product.port.out.category;

import com.personal.marketnote.product.domain.category.Category;

public interface SaveCategoryPort {
    Category save(Category category);
}
