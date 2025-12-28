package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.user.adapter.out.persistence.user.entity.TermsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsJpaRepository extends JpaRepository<TermsJpaEntity, Long> {
    @Query("""
            SELECT t
            FROM TermsJpaEntity t
            WHERE t.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
            ORDER BY t.id ASC
            """)
    List<TermsJpaEntity> findAllByOrderByIdAsc();
}
