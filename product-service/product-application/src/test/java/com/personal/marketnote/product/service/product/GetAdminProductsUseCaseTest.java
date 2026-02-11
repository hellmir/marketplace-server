package com.personal.marketnote.product.service.product;

import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.in.result.product.GetAdminProductsResult;
import com.personal.marketnote.product.port.in.result.product.GetProductsResult;
import com.personal.marketnote.product.port.in.result.product.ProductItemResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAdminProductsUseCaseTest {
    @Mock
    private GetProductUseCase getProductUseCase;

    @InjectMocks
    private GetAdminProductsService getAdminProductsService;

    @Test
    @DisplayName("관리자 상품 목록 조회 시 조회 결과를 그대로 반환한다")
    void getAdminProducts_returnsProductsResult() {
        Long categoryId = 10L;
        List<Long> pricePolicyIds = List.of(1L, 2L);
        Long cursor = 50L;
        int pageSize = 5;
        Sort.Direction sortDirection = Sort.Direction.DESC;
        ProductSortProperty sortProperty = ProductSortProperty.POPULARITY;
        ProductSearchTarget searchTarget = ProductSearchTarget.NAME;
        String searchKeyword = "키워드";

        List<ProductItemResult> items = List.of(mock(ProductItemResult.class));
        GetProductsResult productsResult = GetProductsResult.from(
                true, 99L, 120L, items
        );

        when(getProductUseCase.getProducts(
                categoryId,
                pricePolicyIds,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        )).thenReturn(productsResult);

        GetAdminProductsResult result = getAdminProductsService.getAdminProducts(
                categoryId,
                pricePolicyIds,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        );

        assertThat(result.totalElements()).isEqualTo(120L);
        assertThat(result.nextCursor()).isEqualTo(99L);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.products()).isSameAs(items);

        verify(getProductUseCase).getProducts(
                categoryId,
                pricePolicyIds,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        );
    }

    @Test
    @DisplayName("관리자 상품 목록 조회 시 조회에 실패하면 예외를 전파한다")
    void getAdminProducts_getProductsFails_propagates() {
        RuntimeException exception = new RuntimeException("fail");

        when(getProductUseCase.getProducts(
                isNull(),
                isNull(),
                isNull(),
                eq(10),
                eq(Sort.Direction.DESC),
                eq(ProductSortProperty.POPULARITY),
                eq(ProductSearchTarget.NAME),
                isNull()
        )).thenThrow(exception);

        assertThatThrownBy(() -> getAdminProductsService.getAdminProducts(
                null,
                null,
                null,
                10,
                Sort.Direction.DESC,
                ProductSortProperty.POPULARITY,
                ProductSearchTarget.NAME,
                null
        )).isSameAs(exception);
    }
}
