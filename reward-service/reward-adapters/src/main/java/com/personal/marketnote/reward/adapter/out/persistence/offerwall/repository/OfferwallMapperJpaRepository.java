package com.personal.marketnote.reward.adapter.out.persistence.offerwall.repository;

import com.personal.marketnote.reward.adapter.out.persistence.offerwall.entity.OfferwallMapperJpaEntity;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferwallMapperJpaRepository extends JpaRepository<OfferwallMapperJpaEntity, Long> {
    boolean existsByOfferwallTypeAndRewardKeyAndIsSuccess(OfferwallType offerwallType, String rewardKey, boolean isSuccess);
}
