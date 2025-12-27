package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.user.adapter.out.persistence.user.entity.TermsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsJpaRepository extends JpaRepository<TermsJpaEntity, Long> {
    List<TermsJpaEntity> findAllByOrderByIdAsc();
}
