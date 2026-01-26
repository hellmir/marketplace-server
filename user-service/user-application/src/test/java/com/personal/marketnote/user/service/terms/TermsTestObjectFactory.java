package com.personal.marketnote.user.service.terms;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.TermsSnapshotState;

import java.time.LocalDateTime;

final class TermsTestObjectFactory {
    private TermsTestObjectFactory() {
    }

    static Terms createTerms(
            Long id,
            String content,
            boolean requiredYn,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            EntityStatus status
    ) {
        return Terms.from(TermsSnapshotState.builder()
                .id(id)
                .content(content)
                .requiredYn(requiredYn)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .status(status)
                .build());
    }
}
