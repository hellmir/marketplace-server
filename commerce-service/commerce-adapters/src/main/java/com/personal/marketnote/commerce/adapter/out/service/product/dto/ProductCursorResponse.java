package com.personal.marketnote.commerce.adapter.out.service.product.dto;

import java.util.List;

public record ProductCursorResponse<T>(
        Long totalElements,
        Boolean hasNext,
        Long nextCursor,
        List<T> contents
) {
}
