package com.personal.marketnote.user.adapter.out.mapper;

import com.personal.marketnote.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.TermsJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserOauth2VendorJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserTermsJpaEntity;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.domain.user.UserTerms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserJpaEntityToDomainMapper {
    public static Optional<User> mapToDomain(UserJpaEntity userJpaEntity) {
        return Optional.ofNullable(userJpaEntity)
                .map(entity -> {
                    Role role = mapToDomain(entity.getRoleJpaEntity()).get();
                    List<UserTerms> userTerms = mapToDomain(entity.getUserTermsJpaEntities()).get();
                    List<UserOauth2Vendor> vendors = mapToVendorDomainWithoutUser(entity.getUserOauth2VendorsJpaEntities()).get();

                    User user = User.from(
                            entity.getId(),
                            entity.getNickname(),
                            entity.getEmail(),
                            entity.getPassword(),
                            entity.getFullName(),
                            entity.getPhoneNumber(),
                            entity.getReferenceCode(),
                            entity.getReferredUserCode(),
                            role,
                            vendors,
                            userTerms,
                            entity.getLastLoggedInAt(),
                            entity.getStatus(),
                            entity.getWithdrawalYn()
                    );

                    vendors.forEach(v -> v.addUser(user));

                    return user;
                });
    }

    private static Optional<Role> mapToDomain(RoleJpaEntity roleJpaEntity) {
        return Optional.ofNullable(roleJpaEntity)
                .filter(Objects::nonNull)
                .map(entity -> Role.of(entity.getId(), entity.getName()));
    }

    private static Optional<List<UserOauth2Vendor>> mapToVendorDomainWithoutUser(List<UserOauth2VendorJpaEntity> userOauth2VendorsJpaEntities) {
        return Optional.ofNullable(userOauth2VendorsJpaEntities)
                .filter(Objects::nonNull)
                .map(entities -> entities.stream()
                        .map(entity -> UserOauth2Vendor.of(entity.getAuthVendor(), entity.getOidcId()))
                        .collect(Collectors.toList()));
    }

    private static Optional<List<UserTerms>> mapToDomain(List<UserTermsJpaEntity> userTermsJpaEntities) {
        return Optional.ofNullable(userTermsJpaEntities)
                .filter(Objects::nonNull)
                .map(entities -> entities.stream()
                        .map(entity -> UserTerms.of(
                                Terms.of(
                                        entity.getTermsJpaEntity().getId(),
                                        entity.getTermsJpaEntity().getContent(),
                                        entity.getTermsJpaEntity().getRequiredYn(),
                                        entity.getCreatedAt(),
                                        entity.getModifiedAt(),
                                        entity.getStatus()
                                ), entity.getAgreementYn(), entity.getCreatedAt(), entity.getModifiedAt())
                        )
                        .collect(Collectors.toList()));
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
}
