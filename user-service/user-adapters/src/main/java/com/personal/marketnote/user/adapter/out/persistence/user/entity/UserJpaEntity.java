package com.personal.marketnote.user.adapter.out.persistence.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.repository.TermsJpaRepository;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserJpaEntity extends BaseGeneralEntity {
    @Column(name = "auth_vendor", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private AuthVendor authVendor;

    @Column(name = "oidc_id", length = 255)
    private String oidcId;

    @Column(name = "nickname", nullable = false, length = 31)
    private String nickname;

    @Column(name = "full_name", nullable = false, length = 15)
    private String fullName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "reference_code", length = 15)
    private String referenceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleJpaEntity roleJpaEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userJpaEntity", cascade = {PERSIST, MERGE})
    private List<UserTermsJpaEntity> userTermsJpaEntities;

    @Column(name = "last_logged_in_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastLoggedInAt;

    public static UserJpaEntity from(User user, TermsJpaRepository termsJpaRepository) {
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .authVendor(user.getAuthVendor())
                .oidcId(user.getOidcId())
                .nickname(user.getNickname())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .referenceCode(user.getReferenceCode())
                .roleJpaEntity(RoleJpaEntity.from(user.getRole()))
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();

        userJpaEntity.userTermsJpaEntities = user.getUserTerms().stream()
                .map(ut -> {
                    Long termsId = ut.getTerms().getId();
                    TermsJpaEntity termsRef = termsJpaRepository.getReferenceById(termsId);
                    return UserTermsJpaEntity.of(
                            userJpaEntity, termsRef,
                            Boolean.TRUE.equals(ut.getAgreement()));
                })
                .collect(java.util.stream.Collectors.toList());

        return userJpaEntity;
    }
}
