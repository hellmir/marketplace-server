package com.personal.marketnote.user.adapter.out.persistence.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserJpaEntity extends BaseGeneralEntity {
    @Column(name = "oidc_id", unique = true, length = 255)
    private String oidcId;

    @Column(name = "nickname", nullable = false, length = 31)
    private String nickname;

    @Column(name = "full_name", nullable = false, length = 15)
    private String fullName;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "reference_code", length = 15)
    private String referenceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleJpaEntity roleJpaEntity;

    @Column(name = "last_logged_in_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastLoggedInAt;

    public static UserJpaEntity from(User user) {
        return UserJpaEntity.builder()
                .oidcId(user.getOidcId())
                .nickname(user.getNickname())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .referenceCode(user.getReferenceCode())
                .roleJpaEntity(RoleJpaEntity.from(user.getRole()))
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();
    }
}
