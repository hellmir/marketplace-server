package com.personal.marketnote.product.service.cart;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.exception.PricePolicyNotFoundException;
import com.personal.marketnote.product.port.in.command.UpdateCartProductOptionCommand;
import com.personal.marketnote.product.port.in.usecase.cart.GetCartProductUseCase;
import com.personal.marketnote.product.port.in.usecase.cart.UpdateCartProductOptionsUseCase;
import com.personal.marketnote.product.port.in.usecase.pricepolicy.GetPricePolicyUseCase;
import com.personal.marketnote.product.port.out.cart.UpdateCartProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
@Slf4j
public class UpdateCartProductOptionsService implements UpdateCartProductOptionsUseCase {
    private final GetCartProductUseCase getCartProductUseCase;
    private final GetPricePolicyUseCase getPricePolicyUseCase;
    private final UpdateCartProductPort updateCartProductPort;

    @Override
    public void updateCartProductOptions(UpdateCartProductOptionCommand command) {
        CartProduct cartProduct = getCartProductUseCase.getCartProduct(command.userId(), command.pricePolicyId());

        try {
            PricePolicy pricePolicy = getPricePolicyUseCase.getPricePolicy(command.newOptionIds());
            cartProduct.updatePricePolicy(pricePolicy);
            updateCartProductPort.update(cartProduct, command.pricePolicyId());
        } catch (PricePolicyNotFoundException e) {
            log.warn("Price policy not found for options: {}", command.newOptionIds());
        }
    }
}
