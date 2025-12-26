package com.personal.marketnote.user.port.out;

import com.personal.marketnote.user.domain.user.LoginHistory;

public interface SaveLoginHistoryPort {
    void save(LoginHistory loginHistory);
}
