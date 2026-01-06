package com.personal.marketnote.commerce.domain.inventory;

import com.personal.marketnote.common.domain.exception.illegalargument.invalidvalue.InvalidQuantityException;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.QuantityNoValueException;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.Objects;

import static com.personal.marketnote.common.utility.RegularExpressionConstant.ZERO_AND_POSITIVE_INTEGER_PATTERN;

public class InventoryQuantity {
    private static final String INVENTORY_QUANTITY_NO_VALUE_EXCEPTION = "재고 수량은 필수값입니다.";
    private static final String INVENTORY_QUANTITY_INVALID_EXCEPTION = "재고 수량은 0 또는 양의 정수(0 이상의 숫자값)여야 합니다. 전송된 수량: %s";

    private final int quantity;

    private InventoryQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static InventoryQuantity of(String quantity) {
        validate(quantity);
        return new InventoryQuantity(Integer.parseInt(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryQuantity quantity1 = (InventoryQuantity) o;
        return quantity == quantity1.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    public int getValue() {
        return quantity;
    }

    private static void validate(String quantity) {
        checkQuantityIsNotBlank(quantity);
        checkQuantityPattern(quantity);
    }

    private static void checkQuantityIsNotBlank(String quantity) {
        if (!FormatValidator.hasValue(quantity)) {
            throw new QuantityNoValueException(INVENTORY_QUANTITY_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkQuantityPattern(String quantity) {
        if (!quantity.matches(ZERO_AND_POSITIVE_INTEGER_PATTERN)) {
            throw new InvalidQuantityException(String.format(INVENTORY_QUANTITY_INVALID_EXCEPTION, quantity));
        }
    }

    public Integer reduce(int quantity) {
        return this.quantity - quantity;
    }
}
