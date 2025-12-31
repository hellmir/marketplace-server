package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.in.result.GetProductsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductsUseCase;
import com.personal.marketnote.product.port.out.product.FindProductsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetProductsService implements GetProductsUseCase {
    private final FindProductsPort findProductsPort;

    @Override
    public GetProductsResult getProducts(Long categoryId) {
        List<Product> products = categoryId == null
                ? findProductsPort.findAllActive()
                : findProductsPort.findAllActiveByCategoryId(categoryId);
        return GetProductsResult.from(products);
    }
}


