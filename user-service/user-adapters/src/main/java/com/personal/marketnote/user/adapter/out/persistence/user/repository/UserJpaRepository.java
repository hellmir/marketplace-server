package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND u.oidcId = :oidcId
            """)
    boolean existsByOidcId(@Param("oidcId") String oidcId);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM UserJpaEntity u
            WHERE u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND u.phoneNumber = :phoneNumber
            """)
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

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
            WHERE u.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND u.oidcId = :oidcId
            """)
    Optional<UserJpaEntity> findByOidcId(@Param("oidcId") String oidcId);
}
