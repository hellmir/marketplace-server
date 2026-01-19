package com.personal.marketnote.reward.adapter.out.persistence.point.repository;

import com.personal.marketnote.reward.adapter.out.persistence.point.entity.UserPointJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPointJpaEntity, Long> {
    boolean existsByUserId(Long userId);

    boolean existsByUserKey(String userKey);

    Optional<UserPointJpaEntity> findByUserId(Long userId);

    Optional<UserPointJpaEntity> findByUserKey(String userKey);
}
