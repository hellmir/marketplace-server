package com.personal.marketnote.product.service.cart;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.mapper.CartCommandToDomainMapper;
import com.personal.marketnote.product.port.in.command.AddCartProductCommand;
import com.personal.marketnote.product.port.in.usecase.cart.AddCartProductUseCase;
import com.personal.marketnote.product.port.out.cart.SaveCartProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class AddCartProductService implements AddCartProductUseCase {
    private final SaveCartProductPort saveCartProductPort;

    @Override
    public void addCartProduct(AddCartProductCommand command) {
        saveCartProductPort.save(CartCommandToDomainMapper.mapToDomain(command));
    }
}
