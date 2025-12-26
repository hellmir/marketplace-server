package com.personal.shop.user.adapter.out.persistence.user.entity;

import com.personal.shop.user.security.token.vendor.AuthVendor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity userJpaEntity;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private AuthVendor type;

    public LoginHistoryJpaEntity(UserJpaEntity userJpaEntity, AuthVendor type, LocalDateTime createdAt) {
        this.userJpaEntity = userJpaEntity;
        this.type = type;
        this.createdAt = createdAt;
    }
}
