package com.personal.shop.user.port.out;

import com.personal.shop.user.domain.user.LoginHistory;

public interface SaveLoginHistoryPort {
    void save(LoginHistory loginHistory);
}
