package com.personal.marketnote.product.port.in.usecase.pricepolicy;

import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.result.pricepolicy.RegisterPricePolicyResult;

public interface RegisterPricePolicyUseCase {
    RegisterPricePolicyResult registerPricePolicy(
            Long userId, boolean isAdmin, RegisterPricePolicyCommand command
    );
}
