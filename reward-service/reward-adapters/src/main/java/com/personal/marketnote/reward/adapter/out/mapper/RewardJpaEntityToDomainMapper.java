package com.personal.marketnote.reward.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.adapter.out.persistence.offerwall.entity.OfferwallMapperJpaEntity;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapperSnapshotState;

import java.util.Optional;

public class RewardJpaEntityToDomainMapper {

    public static Optional<OfferwallMapper> mapToDomain(OfferwallMapperJpaEntity entity) {
        if (!FormatValidator.hasValue(entity)) {
            return Optional.empty();
        }

        return Optional.of(
                OfferwallMapper.from(
                        OfferwallMapperSnapshotState.builder()
                                .id(entity.getId())
                                .offerwallType(entity.getOfferwallType())
                                .rewardKey(entity.getRewardKey())
                                .userKey(entity.getUserKey())
                                .campaignKey(entity.getCampaignKey())
                                .campaignType(entity.getCampaignType())
                                .campaignName(entity.getCampaignName())
                                .quantity(entity.getQuantity())
                                .signedValue(entity.getSignedValue())
                                .appKey(entity.getAppKey())
                                .appName(entity.getAppName())
                                .adid(entity.getAdid())
                                .idfa(entity.getIdfa())
                                .isSuccess(entity.getIsSuccess())
                                .attendedAt(entity.getAttendedAt())
                                .requestPayload(entity.getRequestPayload())
                                .requestPayloadJson(entity.getRequestPayloadJson())
                                .createdAt(entity.getCreatedAt())
                                .build()
                )
        );
    }
}

