package com.personal.marketnote.commerce.domain.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum OrderPeriod {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ONE_YEAR(12),
    ALL(null);

    private final Integer months;

    OrderPeriod(Integer months) {
        this.months = months;
    }

    public LocalDateTime startDate(LocalDate now) {
        if (months == null) {
            return null;
        }

        return LocalDateTime.of(now.minusMonths(months), LocalTime.MIDNIGHT);
    }
}
