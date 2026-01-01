package com.personal.marketnote.user.adapter.out.persistence.user.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_oauth2_vendor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserOauth2VendorJpaEntity extends BaseGeneralEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaGeneralEntity userJpaEntity;

    @Column(name = "auth_vendor", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private AuthVendor authVendor;

    @Column(name = "oidc_id", length = 255)
    private String oidcId;

    public static UserOauth2VendorJpaEntity from(UserOauth2Vendor userOauth2Vendor) {
        return UserOauth2VendorJpaEntity.builder()
                .userJpaEntity(UserJpaGeneralEntity.from(userOauth2Vendor.getUser()))
                .authVendor(userOauth2Vendor.getAuthVendor())
                .oidcId(userOauth2Vendor.getOidcId())
                .build();
    }

    public static UserOauth2VendorJpaEntity of(UserJpaGeneralEntity parent, AuthVendor authVendor, String oidcId) {
        return UserOauth2VendorJpaEntity.builder()
                .userJpaEntity(parent)
                .authVendor(authVendor)
                .oidcId(oidcId)
                .build();
    }

    public void updateFrom(UserJpaGeneralEntity userJpaEntity, UserOauth2Vendor userOauth2Vendor) {
        this.userJpaEntity = userJpaEntity;
        authVendor = userOauth2Vendor.getAuthVendor();
        oidcId = userOauth2Vendor.getOidcId();
    }
}
