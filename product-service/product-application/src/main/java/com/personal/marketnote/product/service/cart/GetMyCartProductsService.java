package com.personal.marketnote.product.service.cart;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.port.in.result.cart.GetMyCartProductsResult;
import com.personal.marketnote.product.port.in.usecase.cart.GetMyCartProductsUseCase;
import com.personal.marketnote.product.port.out.cart.FindCartProductsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetMyCartProductsService implements GetMyCartProductsUseCase {
    private final FindCartProductsPort findCartProductsPort;

    @Override
    public GetMyCartProductsResult getMyCartProducts(Long userId) {
        return GetMyCartProductsResult.from(findCartProductsPort.findByUserId(userId));
    }
}
