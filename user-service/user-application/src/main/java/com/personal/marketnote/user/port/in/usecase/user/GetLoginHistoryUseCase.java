package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.domain.user.LoginHistorySortProperty;
import com.personal.marketnote.user.port.in.result.GetLoginHistoryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface GetLoginHistoryUseCase {
    Page<GetLoginHistoryResult> getLoginHistories(
            Long userId,
            int pageSize,
            int pageNumber,
            Sort.Direction sortDirection,
            LoginHistorySortProperty sortProperty
    );
}
