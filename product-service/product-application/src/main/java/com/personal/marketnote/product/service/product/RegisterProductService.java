package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductCreateState;
import com.personal.marketnote.product.domain.product.ProductTagCreateState;
import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.result.pricepolicy.RegisterPricePolicyResult;
import com.personal.marketnote.product.port.in.result.product.RegisterProductResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.RegisterPricePolicyUseCase;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductUseCase;
import com.personal.marketnote.product.port.out.inventory.RegisterInventoryPort;
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
    private final RegisterInventoryPort registerInventoryPort;

    @Override
    public RegisterProductResult registerProduct(RegisterProductCommand registerProductCommand) {
        Long sellerId = registerProductCommand.sellerId();

        Product savedProduct = saveProductPort.save(
                Product.from(
                        ProductCreateState.builder()
                                .sellerId(sellerId)
                                .name(registerProductCommand.name())
                                .brandName(registerProductCommand.brandName())
                                .detail(registerProductCommand.detail())
                                .findAllOptionsYn(registerProductCommand.isFindAllOptions())
                                .tags(
                                        registerProductCommand.tags()
                                                .stream()
                                                .map(tag -> ProductTagCreateState.builder().name(tag).build())
                                                .toList()
                                )
                                .build()
                )
        );

        RegisterPricePolicyResult registerPricePolicyResult = registerPricePolicyUseCase.registerPricePolicy(
                sellerId, false, RegisterPricePolicyCommand.from(savedProduct.getId(), registerProductCommand)
        );

        // FIXME: Kafka 이벤트 Production으로 변경
        registerInventoryPort.registerInventory(registerPricePolicyResult.id());

        return RegisterProductResult.from(savedProduct);
    }
}
