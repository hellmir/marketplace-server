package com.personal.shop.user.adapter.out.persistence.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.shop.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.shop.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.shop.user.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserJpaEntity extends BaseGeneralEntity {
    @Column(name = "oidc_id", unique = true, length = 255)
    private String oidcId;

    @Column(name = "nickname", length = 63)
    private String nickname;

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
                .referenceCode(user.getReferenceCode())
                .roleJpaEntity(RoleJpaEntity.from(user.getRole()))
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();
    }
}
