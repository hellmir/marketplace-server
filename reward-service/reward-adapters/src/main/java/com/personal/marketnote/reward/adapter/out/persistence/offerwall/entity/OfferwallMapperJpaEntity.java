package com.personal.marketnote.reward.adapter.out.persistence.offerwall.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapperSnapshotState;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "offerwall_mapper",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_offerwall_type_reward_key",
                        columnNames = {"offerwall_type", "reward_key"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OfferwallMapperJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "offerwall_type", nullable = false, length = 15)
    private OfferwallType offerwallType;

    @Column(name = "reward_key", nullable = false, length = 256)
    private String rewardKey;

    @Column(name = "user_key", nullable = false, length = 128)
    private String userKey;

    @Column(name = "campaign_key", nullable = false, length = 50)
    private String campaignKey;

    @Column(name = "campaign_type")
    private Integer campaignType;

    @Column(name = "campaign_name", length = 256)
    private String campaignName;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "signed_value", nullable = false, length = 256)
    private String signedValue;

    @Column(name = "app_key")
    private Integer appKey;

    @Column(name = "app_name", length = 50)
    private String appName;

    @Column(name = "adid", length = 128)
    private String adid;

    @Column(name = "idfa", length = 128)
    private String idfa;

    @Column(name = "success_yn", nullable = false)
    private Boolean isSuccess;

    @Column(name = "attended_at")
    private LocalDateTime attendedAt;

    @Column(name = "request_payload", nullable = false, columnDefinition = "TEXT")
    private String requestPayload;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "request_payload_json", nullable = false, columnDefinition = "jsonb")
    private JsonNode requestPayloadJson;

    @Column(name = "response_payload", columnDefinition = "TEXT")
    private String responsePayload;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response_payload_json", columnDefinition = "jsonb")
    private JsonNode responsePayloadJson;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static OfferwallMapperJpaEntity from(OfferwallMapper offerwallMapper) {
        if (!FormatValidator.hasValue(offerwallMapper)) {
            return null;
        }

        return OfferwallMapperJpaEntity.builder()
                .id(offerwallMapper.getId())
                .offerwallType(offerwallMapper.getOfferwallType())
                .rewardKey(offerwallMapper.getRewardKey())
                .userKey(offerwallMapper.getUserKey())
                .campaignKey(offerwallMapper.getCampaignKey())
                .campaignType(offerwallMapper.getCampaignType())
                .campaignName(offerwallMapper.getCampaignName())
                .quantity(offerwallMapper.getQuantity())
                .signedValue(offerwallMapper.getSignedValue())
                .appKey(offerwallMapper.getAppKey())
                .appName(offerwallMapper.getAppName())
                .adid(offerwallMapper.getAdid())
                .idfa(offerwallMapper.getIdfa())
                .isSuccess(offerwallMapper.getIsSuccess())
                .attendedAt(offerwallMapper.getAttendedAt())
                .requestPayload(offerwallMapper.getRequestPayload())
                .requestPayloadJson(offerwallMapper.getRequestPayloadJson())
                .responsePayload(offerwallMapper.getResponsePayload())
                .responsePayloadJson(offerwallMapper.getResponsePayloadJson())
                .createdAt(offerwallMapper.getCreatedAt())
                .build();
    }

    public OfferwallMapper toDomain() {
        return OfferwallMapper.from(
                OfferwallMapperSnapshotState.builder()
                        .id(id)
                        .offerwallType(offerwallType)
                        .rewardKey(rewardKey)
                        .userKey(userKey)
                        .campaignKey(campaignKey)
                        .campaignType(campaignType)
                        .campaignName(campaignName)
                        .quantity(quantity)
                        .signedValue(signedValue)
                        .appKey(appKey)
                        .appName(appName)
                        .adid(adid)
                        .idfa(idfa)
                        .isSuccess(isSuccess)
                        .attendedAt(attendedAt)
                        .requestPayload(requestPayload)
                        .requestPayloadJson(requestPayloadJson)
                        .responsePayload(responsePayload)
                        .responsePayloadJson(responsePayloadJson)
                        .createdAt(createdAt)
                        .build()
        );
    }

    public void updateResponse(String payload, JsonNode payloadJson) {
        this.responsePayload = payload;
        this.responsePayloadJson = payloadJson;
    }
}
