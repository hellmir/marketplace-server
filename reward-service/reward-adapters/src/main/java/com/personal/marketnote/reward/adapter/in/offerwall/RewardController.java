package com.personal.marketnote.reward.adapter.in.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.marketnote.reward.adapter.in.offerwall.mapper.RewardRequestToCommandMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/rewards")
@Tag(name = "리워드 API", description = "리워드 관련 API")
@RequiredArgsConstructor
public class RewardController {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Seoul");

    private final RegisterOfferwallRewardUseCase registerOfferwallRewardUseCase;
    private final ObjectMapper objectMapper;

    /**
     * 아드팝콘 리워드 지급 콜백 엔드포인트
     *
     * @param rewardKey    리워드 키
     * @param usn          사용자 키
     * @param campaignKey  캠페인 키
     * @param campaignType 캠페인 타입
     * @param campaignName 캠페인 이름
     * @param quantity     수량
     * @param signedValue  서명 값(검증 필요)
     * @param appKey       앱 키
     * @param appName      앱 이름
     * @param adid         구글 광고 ID
     * @param idfa         IDFA
     * @param timeStamp    캠페인 완료일시
     * @Author 성효빈
     * @Date 2026-01-16
     */
    @PostMapping(value = "/offerwalls/adpopcorn/callback")
    public ResponseEntity<String> handleAdpopcornCallback(
            @RequestParam("reward_key") String rewardKey,
            @RequestParam("usn") String usn,
            @RequestParam("campaign_key") String campaignKey,
            @RequestParam(value = "campaign_type", required = false) Integer campaignType,
            @RequestParam(value = "campaign_name", required = false) String campaignName,
            @RequestParam(value = "quantity") Long quantity,
            @RequestParam(value = "signed_value") String signedValue,
            @RequestParam(value = "app_key", required = false) Integer appKey,
            @RequestParam(value = "app_name", required = false) String appName,
            @RequestParam(value = "adid", required = false) String adid,
            @RequestParam(value = "idfa", required = false) String idfa,
            @RequestParam(value = "time_stamp", required = false) String timeStamp
    ) {
        LocalDateTime attendedAt = parseTimestamp(timeStamp);

        JsonNode payloadJson = buildPayloadJson(
                rewardKey,
                usn,
                campaignKey,
                campaignType,
                campaignName,
                quantity,
                signedValue,
                appKey,
                appName,
                adid,
                idfa,
                timeStamp
        );
        String payloadString = payloadJson.toString();

        OfferwallCallbackCommand command = RewardRequestToCommandMapper.mapToOfferwallCallbackCommand(
                OfferwallType.ADPOPCORN,
                rewardKey,
                usn,
                campaignKey,
                campaignType,
                campaignName,
                quantity,
                signedValue,
                appKey,
                appName,
                adid,
                idfa,
                attendedAt,
                true,
                payloadString,
                payloadJson
        );

        registerOfferwallRewardUseCase.register(command);

        return ResponseEntity.ok("OK");
    }

    private JsonNode buildPayloadJson(
            String rewardKey,
            String usn,
            String campaignKey,
            Integer campaignType,
            String campaignName,
            Long quantity,
            String signedValue,
            Integer appKey,
            String appName,
            String adid,
            String idfa,
            String timeStamp
    ) {
        ObjectNode node = objectMapper.createObjectNode();

        putIfNotBlank(node, "reward_key", rewardKey);
        putIfNotBlank(node, "usn", usn);
        putIfNotBlank(node, "campaign_key", campaignKey);
        if (campaignType != null) {
            node.put("campaign_type", campaignType);
        }
        putIfNotBlank(node, "campaign_name", campaignName);
        if (quantity != null) {
            node.put("quantity", quantity);
        }
        putIfNotBlank(node, "signed_value", signedValue);
        if (appKey != null) {
            node.put("app_key", appKey);
        }
        putIfNotBlank(node, "app_name", appName);
        putIfNotBlank(node, "adid", adid);
        putIfNotBlank(node, "idfa", idfa);
        putIfNotBlank(node, "time_stamp", timeStamp);

        return node;
    }

    private void putIfNotBlank(ObjectNode node, String field, String value) {
        if (StringUtils.hasText(value)) {
            node.put(field, value);
        }
    }

    private LocalDateTime parseTimestamp(String raw) {
        if (!StringUtils.hasText(raw)) {
            return null;
        }

        try {
            long epochSeconds = Long.parseLong(raw);
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), DEFAULT_ZONE_ID);
        } catch (NumberFormatException ignored) {
        }

        try {
            return LocalDateTime.parse(raw, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception ignored) {
        }

        try {
            return LocalDateTime.parse(raw, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ignored) {
        }

        return null;
    }
}
