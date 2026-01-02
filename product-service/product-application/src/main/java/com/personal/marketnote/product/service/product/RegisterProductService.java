package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.result.RegisterProductResult;
import com.personal.marketnote.product.port.in.usecase.product.RegisterPricePolicyUseCase;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductUseCase;
import com.personal.marketnote.product.port.out.product.SaveProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterProductService implements RegisterProductUseCase {
    private final RegisterPricePolicyUseCase registerPricePolicyUseCase;
    private final SaveProductPort saveProductPort;

    @Override
    public RegisterProductResult registerProduct(RegisterProductCommand registerProductCommand) {
        Long sellerId = registerProductCommand.sellerId();

        Product savedProduct = saveProductPort.save(
                Product.of(
                        sellerId,
                        registerProductCommand.name(),
                        registerProductCommand.brandName(),
                        registerProductCommand.detail(),
                        registerProductCommand.isFindAllOptions(),
                        registerProductCommand.tags()
                )
        );

        registerPricePolicyUseCase.registerPricePolicy(
                sellerId, false, RegisterPricePolicyCommand.from(savedProduct.getId(), registerProductCommand)
        );

        return RegisterProductResult.from(savedProduct);
    }
}


