package com.personal.marketnote.user.adapter.out.mapper;

import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.TermsJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserTermsJpaEntity;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserTerms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserJpaEntityToDomainMapper {
    public static Optional<User> mapToDomain(UserJpaEntity userJpaEntity) {
        return Optional.ofNullable(userJpaEntity)
                .filter(Objects::nonNull)
                .map(
                        entity -> User.of(
                                userJpaEntity.getId(),
                                userJpaEntity.getAuthVendor(),
                                userJpaEntity.getOidcId(),
                                userJpaEntity.getNickname(),
                                userJpaEntity.getFullName(),
                                userJpaEntity.getPhoneNumber(),
                                userJpaEntity.getReferenceCode(),
                                mapToDomain(userJpaEntity.getRoleJpaEntity()).get(),
                                mapToDomain(userJpaEntity.getUserTermsJpaEntities()).get(),
                                userJpaEntity.getLastLoggedInAt()));
    }

    private static Optional<Role> mapToDomain(RoleJpaEntity roleJpaEntity) {
        return Optional.ofNullable(roleJpaEntity)
                .filter(Objects::nonNull)
                .map(entity -> Role.of(entity.getId(), entity.getName()));
    }

    public static Optional<Terms> mapToDomain(TermsJpaEntity termsJpaEntity) {
        return Optional.ofNullable(termsJpaEntity)
                .filter(Objects::nonNull)
                .map(
                        entity -> Terms.of(
                                termsJpaEntity.getId(),
                                termsJpaEntity.getContent(),
                                termsJpaEntity.getRequiredYn(),
                                termsJpaEntity.getCreatedAt(),
                                termsJpaEntity.getModifiedAt(),
                                termsJpaEntity.getStatus())
                );
    }

    private static Optional<List<UserTerms>> mapToDomain(List<UserTermsJpaEntity> userTermsJpaEntities) {
        return Optional.ofNullable(userTermsJpaEntities)
                .filter(Objects::nonNull)
                .map(entities -> entities.stream()
                        .map(entity -> UserTerms.of(
                                entity.getAgreementYn(), entity.getCreatedAt(), entity.getModifiedAt())
                        )
                        .collect(Collectors.toList()));
    }
}
