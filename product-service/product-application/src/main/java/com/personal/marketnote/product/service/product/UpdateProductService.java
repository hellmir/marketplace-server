package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.exception.NotProductOwnerException;
import com.personal.marketnote.product.port.in.command.UpdateProductCommand;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.in.usecase.product.UpdateProductUseCase;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.product.UpdateProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.FIRST_ERROR_CODE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED)
public class UpdateProductService implements UpdateProductUseCase {
    private final GetProductUseCase getProductUseCase;
    private final FindProductPort findProductPort;
    private final UpdateProductPort updateProductPort;

    @Override
    public void update(Long userId, boolean isAdmin, UpdateProductCommand command) {
        Long id = command.id();
        if (!isAdmin && !findProductPort.existsByIdAndSellerId(id, userId)) {
            throw new NotProductOwnerException(FIRST_ERROR_CODE, id);
        }

        Product product = getProductUseCase.getProduct(id);
        product.update(
                command.name(), command.brandName(), command.detail(), command.isFindAllOptions(), command.tags()
        );

        updateProductPort.update(product);
    }
}


