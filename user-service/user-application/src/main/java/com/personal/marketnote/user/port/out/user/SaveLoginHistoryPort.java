package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.LoginHistory;

public interface SaveLoginHistoryPort {
    void saveLoginHistory(LoginHistory loginHistory);
}
