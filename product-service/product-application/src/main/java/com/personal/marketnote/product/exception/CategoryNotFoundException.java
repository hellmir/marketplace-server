package com.personal.marketnote.product.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(String message, String code, Long parentCategoryId) {
        super(String.format(message, code, parentCategoryId));
    }
}
