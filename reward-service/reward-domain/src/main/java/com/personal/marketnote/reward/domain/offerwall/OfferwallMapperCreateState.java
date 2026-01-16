package com.personal.marketnote.reward.domain.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OfferwallMapperCreateState {
    private final OfferwallType offerwallType;
    private final String rewardKey;
    private final String userKey;
    private final String campaignKey;
    private final Integer campaignType;
    private final String campaignName;
    private final Long quantity;
    private final String signedValue;
    private final Integer appKey;
    private final String appName;
    private final String adid;
    private final String idfa;
    private final Boolean isSuccess;
    private final LocalDateTime attendedAt;
    private final String requestPayload;
    private final JsonNode requestPayloadJson;
    private final String responsePayload;
    private final JsonNode responsePayloadJson;
}
