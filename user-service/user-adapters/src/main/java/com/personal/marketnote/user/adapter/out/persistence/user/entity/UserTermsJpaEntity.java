package com.personal.marketnote.user.adapter.out.persistence.user.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.user.domain.user.Terms;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_terms", uniqueConstraints = {
        @UniqueConstraint(name = "ux_user_terms_user_terms", columnNames = {"user_id", "terms_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserTermsJpaEntity extends BaseGeneralEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terms_id", nullable = false)
    private TermsJpaEntity termsJpaEntity;

    @Column(name = "agreement_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean agreementYn;

    public static UserTermsJpaEntity of(UserJpaEntity userJpaEntity, TermsJpaEntity termsJpaEntity, Boolean agreementYn) {
        return UserTermsJpaEntity.builder()
                .userJpaEntity(userJpaEntity)
                .termsJpaEntity(termsJpaEntity)
                .agreementYn(agreementYn)
                .build();
    }

    public static UserTermsJpaEntity from(UserJpaEntity userJpaEntity, Terms terms) {
        return UserTermsJpaEntity.builder()
                .userJpaEntity(userJpaEntity)
                .termsJpaEntity(TermsJpaEntity.from(terms))
                .build();
    }
}
