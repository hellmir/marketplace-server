package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND EXISTS (
                SELECT 1
                FROM UserOauth2VendorJpaEntity uov
                WHERE 1 = 1
                AND uov.userJpaEntity = u
                AND uov.authVendor = :authVendor
                AND uov.oidcId = :oidcId
                )
            """)
    boolean existsByAuthVendorAndOidcId(@Param("authVendor") AuthVendor authVendor, @Param("oidcId") String oidcId);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.nickname = :nickname
            """)
    boolean existsByNickname(@Param("nickname") String nickname);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.email = :email
            """)
    boolean existsByEmail(@Param("email") String email);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND u.phoneNumber = :phoneNumber
            """)
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.referenceCode = :referenceCode
            """)
    boolean existsByReferenceCode(@Param("referenceCode") String referenceCode);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.id = :id
            """)
    Optional<UserJpaEntity> findById(@Param("id") Long id);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND EXISTS (
              SELECT 1
              FROM UserOauth2VendorJpaEntity uov
              WHERE uov.userJpaEntity = u
              AND uov.authVendor = :authVendor
              AND uov.oidcId = :oidcId
              )
            """)
    Optional<UserJpaEntity> findByAuthVendorAndOidcId(@Param("authVendor") AuthVendor authVendor, @Param("oidcId") String oidcId);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.phoneNumber = :phoneNumber
            """)
    Optional<UserJpaEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.email = :email
            """)
    Optional<UserJpaEntity> findByEmail(@Param("email") String email);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.id = :id
            """)
    Optional<UserJpaEntity> findAllStatusUserById(@Param("id") Long id);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE 1 = 1
                AND u.email = :email
            """)
    Optional<UserJpaEntity> findAllStatusUserByEmail(@Param("email") String email);
}
