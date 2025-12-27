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
    private Long id;
    private String content;
    private Boolean requiredYn;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private EntityStatus status;

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
