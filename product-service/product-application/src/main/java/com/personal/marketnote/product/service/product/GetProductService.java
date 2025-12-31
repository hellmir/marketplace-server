package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;

    @Override
    public Product getProduct(Long id) {
        return findProductPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
