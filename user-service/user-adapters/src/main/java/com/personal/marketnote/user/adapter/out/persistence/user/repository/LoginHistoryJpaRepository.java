package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.user.adapter.out.persistence.user.entity.LoginHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryJpaRepository extends JpaRepository<LoginHistoryJpaEntity, Long> {
}


