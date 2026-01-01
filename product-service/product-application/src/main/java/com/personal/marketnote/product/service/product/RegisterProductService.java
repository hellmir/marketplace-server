package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.result.RegisterProductResult;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductUseCase;
import com.personal.marketnote.product.port.out.product.SaveProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED)
public class RegisterProductService implements RegisterProductUseCase {
    private final SaveProductPort saveProductPort;

    @Override
    public RegisterProductResult registerProduct(RegisterProductCommand command) {
        Product savedProduct = saveProductPort.save(
                Product.of(
                        command.sellerId(),
                        command.name(),
                        command.brandName(),
                        command.detail(),
                        command.price(),
                        command.accumulatedPoint()
                )
        );

        return RegisterProductResult.from(savedProduct);
    }
}


