package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.result.RegisterPricePolicyResult;

public interface RegisterPricePolicyUseCase {
    RegisterPricePolicyResult registerPricePolicy(
            Long userId, boolean isAdmin, RegisterPricePolicyCommand command
    );
}
