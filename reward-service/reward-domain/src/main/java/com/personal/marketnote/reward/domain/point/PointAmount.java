package com.personal.marketnote.reward.domain.point;

import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.exception.InvalidPointAmountException;
import com.personal.marketnote.reward.domain.exception.PointAmountNoValueException;

import static com.personal.marketnote.common.utility.RegularExpressionConstant.ZERO_OR_POSITIVE_INTEGER_PATTERN;

public class PointAmount {
    private static final String POINT_AMOUNT_NO_VALUE_EXCEPTION_MESSAGE = "포인트 금액은 필수값입니다.";
    private static final String INVALID_POINT_AMOUNT_EXCEPTION_MESSAGE = "포인트 금액은 0 또는 양의 정수(0 이상의 숫자값)여야 합니다. 차감 후 포인트: %s";

    private final long amount;

    private PointAmount(long amount) {
        this.amount = amount;
    }

    public static PointAmount of(String amount) {
        validate(amount);
        return new PointAmount(FormatConverter.parseToLong(amount));
    }

    private static void validate(String amount) {
        checkAmountIsNotBlank(amount);
        checkAmountPattern(amount);
    }

    private static void checkAmountIsNotBlank(String amount) {
        if (!FormatValidator.hasValue(amount)) {
            throw new PointAmountNoValueException(POINT_AMOUNT_NO_VALUE_EXCEPTION_MESSAGE);
        }
    }

    private static void checkAmountPattern(String amount) {
        if (!amount.matches(ZERO_OR_POSITIVE_INTEGER_PATTERN)) {
            throw new InvalidPointAmountException(String.format(INVALID_POINT_AMOUNT_EXCEPTION_MESSAGE, amount));
        }
    }

    public static PointAmount generateChangedAmount(boolean isAccrual, PointAmount currentAmount, Long requestedAmount) {
        PointAmount addedAmount = PointAmount.of(
                String.valueOf(requestedAmount)
        );

        if (isAccrual) {
            return accumulate(currentAmount, addedAmount);
        }

        return reduce(currentAmount, addedAmount);
    }

    private static PointAmount accumulate(PointAmount currentAmount, PointAmount addedAmount) {
        return PointAmount.of(
                String.valueOf(currentAmount.getValue() + addedAmount.getValue())
        );
    }

    private static PointAmount reduce(PointAmount currentAmount, PointAmount reducedAmount) {
        return PointAmount.of(
                String.valueOf(currentAmount.getValue() - reducedAmount.getValue())
        );
    }

    public long getValue() {
        return amount;
    }
}
