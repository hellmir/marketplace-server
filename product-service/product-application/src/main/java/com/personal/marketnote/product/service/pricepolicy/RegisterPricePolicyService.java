package com.personal.marketnote.product.service.pricepolicy;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.exception.NotProductOwnerException;
import com.personal.marketnote.product.mapper.ProductCommandToDomainMapper;
import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.result.pricepolicy.RegisterPricePolicyResult;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.RegisterPricePolicyUseCase;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.inventory.RegisterInventoryPort;
import com.personal.marketnote.product.port.out.pricepolicy.SavePricePolicyPort;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.UpdateOptionPricePolicyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.FIRST_ERROR_CODE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterPricePolicyService implements RegisterPricePolicyUseCase {
    private final GetProductUseCase getProductUseCase;
    private final FindProductPort findProductPort;
    private final SavePricePolicyPort savePricePolicyPort;
    private final UpdateOptionPricePolicyPort updateOptionPricePolicyPort;
    private final RegisterInventoryPort registerInventoryPort;

    @Override
    public RegisterPricePolicyResult registerPricePolicy(
            Long userId, boolean isAdmin, RegisterPricePolicyCommand command
    ) {
        Long productId = command.productId();
        if (!isAdmin && !findProductPort.existsByIdAndSellerId(productId, userId)) {
            throw new NotProductOwnerException(FIRST_ERROR_CODE, productId);
        }

        Product product = getProductUseCase.getProduct(productId);

        PricePolicy pricePolicy = ProductCommandToDomainMapper.mapToDomain(product, command);
        Long id = savePricePolicyPort.save(pricePolicy);

        if (command.optionIds() != null && !command.optionIds().isEmpty()) {
            updateOptionPricePolicyPort.assignPricePolicyToOptions(id, command.optionIds());
        }

        registerInventoryPort.registerInventory(id);

        return RegisterPricePolicyResult.of(id);
    }
}
