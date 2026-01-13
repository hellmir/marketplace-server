package com.personal.marketnote.community.adapter.out.service.product.response;

import com.personal.marketnote.common.adapter.in.response.CursorResponse;

public record OrderedProductsResponse(
        CursorResponse<ProductsInfoResponse> products
) {
}
