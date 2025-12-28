package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Terms {
    private final Long id;
    private final String content;
    private final Boolean requiredYn;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final EntityStatus status;

    public static Terms of(
            Long id,
            String content,
            Boolean requiredYn,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            EntityStatus status
    ) {
        return Terms.builder()
                .id(id)
                .content(content)
                .requiredYn(requiredYn)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .status(status)
                .build();
    }
}
