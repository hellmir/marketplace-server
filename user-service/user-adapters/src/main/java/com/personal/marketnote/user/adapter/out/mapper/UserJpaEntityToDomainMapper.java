package com.personal.marketnote.user.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.User;

import java.util.Optional;

public class UserJpaEntityToDomainMapper {
    public static Optional<User> mapToDomain(UserJpaEntity userJpaEntity) {
        return Optional.ofNullable(userJpaEntity)
                .filter(FormatValidator::hasValue)
                .map(
                        entity -> User.of(
                                userJpaEntity.getId(),
                                userJpaEntity.getOidcId(),
                                userJpaEntity.getNickname(),
                                userJpaEntity.getReferenceCode(),
                                mapToDomain(userJpaEntity.getRoleJpaEntity()).get(),
                                userJpaEntity.getLastLoggedInAt()
                        )
                );
    }

    private static Optional<Role> mapToDomain(RoleJpaEntity roleJpaEntity) {
        return Optional.ofNullable(roleJpaEntity)
                .filter(FormatValidator::hasValue)
                .map(entity -> Role.of(entity.getId(), entity.getName()));
    }
}
