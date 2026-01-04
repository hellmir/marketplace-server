package com.personal.marketnote.product.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class PricePolicyNotFoundException extends EntityNotFoundException {
    private static final String PRICE_POLICY_NOT_FOUND_EXCEPTION_MESSAGE = "가격 정책을 찾을 수 없습니다. 전송된 가격 정책 ID: %d";

    public PricePolicyNotFoundException(Long id) {
        super(String.format(PRICE_POLICY_NOT_FOUND_EXCEPTION_MESSAGE, id));
    }
}
