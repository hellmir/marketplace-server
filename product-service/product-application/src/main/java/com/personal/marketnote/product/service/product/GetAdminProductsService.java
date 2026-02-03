package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.in.result.product.GetAdminProductsResult;
import com.personal.marketnote.product.port.in.result.product.GetProductsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetAdminProductsUseCase;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetAdminProductsService implements GetAdminProductsUseCase {
    private final GetProductUseCase getProductUseCase;

    @Override
    public GetAdminProductsResult getAdminProducts(
            Long categoryId,
            List<Long> pricePolicyIds,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        GetProductsResult products = getProductUseCase.getProducts(
                categoryId,
                pricePolicyIds,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        );

        return GetAdminProductsResult.of(
                products.totalElements(),
                products.nextCursor(),
                products.hasNext(),
                products.products()
        );
    }
}
