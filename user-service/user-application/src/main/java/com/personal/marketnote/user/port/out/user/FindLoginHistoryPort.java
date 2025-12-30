package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindLoginHistoryPort {
    Page<LoginHistory> findLoginHistoriesByUserId(Pageable pageable, Long userId);
}
