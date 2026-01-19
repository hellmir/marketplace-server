package com.personal.marketnote.reward.domain.point;

import com.personal.marketnote.common.utility.FormatConverter;
import lombok.Getter;

@Getter
public enum UserPointHistoryFilter {
    ALL("전체"),
    ACCRUAL("적립"),
    DEDUCTION("사용");

    private final String description;
    private final String camelCaseValue;

    UserPointHistoryFilter(String description) {
        this.description = description;
        this.camelCaseValue = FormatConverter.snakeToCamel(name());
    }

    public boolean isAll() {
        return this == ALL;
    }

    public boolean isAccrual() {
        return this == ACCRUAL;
    }

    public boolean isDeduction() {
        return this == DEDUCTION;
    }
}

