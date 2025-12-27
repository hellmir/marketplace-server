package com.personal.marketnote.user.adapter.out.mapper;

import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.User;

import java.util.Objects;
import java.util.Optional;

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
                                userJpaEntity.getLastLoggedInAt()
                        )
                );
    }

    private static Optional<Role> mapToDomain(RoleJpaEntity roleJpaEntity) {
        return Optional.ofNullable(roleJpaEntity)
                .filter(Objects::nonNull)
                .map(entity -> Role.of(entity.getId(), entity.getName()));
    }
}
