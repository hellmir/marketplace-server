package com.personal.shop.user.adapter.out.persistence.user.repository;

import com.personal.shop.user.adapter.out.persistence.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    @Query(
            value = """
                    SELECT COUNT(*) > 0
                    FROM userJpaEntity u
                    WHERE u.status = com.personal.shop.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                         AND u.oidcId = :oidcId
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    boolean existsByOidcId(@Param("oidcId") String oidcId);

    @Query("""
            SELECT u
            FROM UserJpaEntity u
            WHERE u.status = com.personal.shop.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.id = :id
            """)
    Optional<UserJpaEntity> findById(@Param("id") Long id);

    @Query(value = """
            SELECT u
            FROM UserJpaEntity u
            WHERE u.status = com.personal.shop.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
                AND u.oidcId = :oidcId
            """)
    Optional<UserJpaEntity> findByOidcId(@Param("oidcId") String oidcId);
}
