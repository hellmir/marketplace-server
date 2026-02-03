package com.personal.marketnote.product.adapter.in.web.product.response;

import com.personal.marketnote.common.adapter.in.response.CursorResponse;
import com.personal.marketnote.product.port.in.result.product.GetAdminProductsResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetAdminProductsResponse {
    private CursorResponse<AdminProductItemResponse> products;

    public static GetAdminProductsResponse from(GetAdminProductsResult result) {
        return new GetAdminProductsResponse(
                new CursorResponse<>(
                        result.totalElements(),
                        result.hasNext(),
                        result.nextCursor(),
                        result.products().stream()
                                .map(AdminProductItemResponse::from)
                                .collect(Collectors.toList())
                )
        );
    }
}
