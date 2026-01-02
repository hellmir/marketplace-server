package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.port.in.result.GetProductOptionsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductOptionsUseCase;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetProductOptionsService implements GetProductOptionsUseCase {
    private final FindProductOptionCategoryPort findProductOptionCategoryPort;

    @Override
    public GetProductOptionsResult getProductOptions(Long productId) {
        List<ProductOptionCategory> categories
                = findProductOptionCategoryPort.findActiveWithOptionsByProductId(productId);

        return GetProductOptionsResult.from(categories);
    }
}
