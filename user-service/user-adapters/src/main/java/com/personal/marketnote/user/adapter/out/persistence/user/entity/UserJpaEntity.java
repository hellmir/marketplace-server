package com.personal.marketnote.user.adapter.out.persistence.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.repository.TermsJpaRepository;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.domain.user.UserTerms;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserJpaEntity extends BaseGeneralEntity {
    @Column(name = "nickname", nullable = false, unique = true, length = 31)
    private String nickname;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "password", length = 511)
    private String password;

    @Column(name = "full_name", length = 15)
    private String fullName;

    @Column(name = "phone_number", unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "reference_code", unique = true, length = 15)
    private String referenceCode;

    @Column(name = "referred_user_code", unique = true, length = 15)
    private String referredUserCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleJpaEntity roleJpaEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userJpaEntity", cascade = {PERSIST, MERGE})
    @OrderBy("id ASC")
    private List<UserOauth2VendorJpaEntity> userOauth2VendorsJpaEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userJpaEntity", cascade = {PERSIST, MERGE})
    @OrderBy("termsJpaEntity.id ASC")
    private List<UserTermsJpaEntity> userTermsJpaEntities;

    @Column(name = "last_logged_in_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastLoggedInAt;

    @Column(name = "withdrawn_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean withdrawalYn;

    public static UserJpaEntity from(User user, TermsJpaRepository termsJpaRepository) {
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .referenceCode(user.getReferenceCode())
                .referredUserCode(user.getReferredUserCode())
                .roleJpaEntity(RoleJpaEntity.from(user.getRole()))
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();

        // set vendors after building to avoid recursive mapping
        userJpaEntity.userOauth2VendorsJpaEntities = user.getUserOauth2Vendors().stream()
                .map(v -> UserOauth2VendorJpaEntity.of(userJpaEntity, v.getAuthVendor(), v.getOidcId()))
                .collect(Collectors.toList());

        userJpaEntity.userTermsJpaEntities = user.getUserTerms().stream()
                .map(ut -> {
                    Long termsId = ut.getTerms().getId();
                    TermsJpaEntity termsRef = termsJpaRepository.getReferenceById(termsId);
                    return UserTermsJpaEntity.of(
                            userJpaEntity, termsRef,
                            Boolean.TRUE.equals(ut.getAgreementYn()));
                })
                .collect(java.util.stream.Collectors.toList());

        return userJpaEntity;
    }

    public static UserJpaEntity from(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .referenceCode(user.getReferenceCode())
                .referredUserCode(user.getReferredUserCode())
                .roleJpaEntity(RoleJpaEntity.from(user.getRole()))
                .lastLoggedInAt(user.getLastLoggedInAt())
                .withdrawalYn(user.isWithdrawn())
                .build();

        userJpaEntity.userOauth2VendorsJpaEntities = user.getUserOauth2Vendors().stream()
                .map(v -> UserOauth2VendorJpaEntity.of(userJpaEntity, v.getAuthVendor(), v.getOidcId()))
                .collect(Collectors.toList());

        return userJpaEntity;
    }

    public void updateFrom(User user) {
        updateActivation(user);
        nickname = user.getNickname();
        email = user.getEmail();
        password = user.getPassword();
        phoneNumber = user.getPhoneNumber();
        referredUserCode = user.getReferredUserCode();
        roleJpaEntity = RoleJpaEntity.from(user.getRole());
        lastLoggedInAt = user.getLastLoggedInAt();
        withdrawalYn = user.isWithdrawn();

        // 회원 계정 정보 업데이트
        for (int i = 0; i < userOauth2VendorsJpaEntities.size(); i++) {
            UserOauth2VendorJpaEntity userOauth2VendorJpaEntity = userOauth2VendorsJpaEntities.get(i);
            UserOauth2Vendor userOauth2Vendor = user.getUserOauth2Vendors().get(i);
            userOauth2VendorJpaEntity.updateFrom(this, userOauth2Vendor);
        }

        // 회원 약관 동의 정보 업데이트
        for (int i = 0; i < userTermsJpaEntities.size(); i++) {
            UserTermsJpaEntity userTermsJpaEntity = userTermsJpaEntities.get(i);
            UserTerms targetUserTerms = user.getUserTerms().get(i);
            userTermsJpaEntity.updateFrom(targetUserTerms);
        }
    }

    private void updateActivation(User user) {
        if (user.isActive()) {
            activateEntity();
            return;
        }

        deactivateEntity();
    }
}
