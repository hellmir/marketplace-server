package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsCreateState {
    private final String content;
    private final Boolean requiredYn;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final EntityStatus status;
}

