package com.personal.marketnote.user.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserTerms {
    private User user;
    private Terms terms;
    private Boolean agreementYn;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static UserTerms of(Terms terms, Boolean agreementYn, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return UserTerms.builder()
                .terms(terms)
                .agreementYn(agreementYn)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static UserTerms of(User user, Terms terms) {
        return UserTerms.builder()
                .user(user)
                .terms(terms)
                .build();
    }

    public void acceptOrCancel() {
        agreementYn = !agreementYn;
    }
}
