package com.personal.marketnote.reward.adapter.in.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.marketnote.common.domain.exception.token.AuthenticationFailedException;
import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.adapter.in.offerwall.mapper.RewardRequestToCommandMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationTargetType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorName;
import com.personal.marketnote.reward.exception.DuplicateOfferwallRewardException;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;
import com.personal.marketnote.reward.port.in.command.vendorcommunication.RewardVendorCommunicationHistoryCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.in.usecase.vendorcommunication.RewardRecordVendorCommunicationHistoryUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/offerwalls")
@Tag(name = "리워드 API", description = "리워드 관련 API")
@RequiredArgsConstructor
@Slf4j
public class OfferwallController {
    private final RegisterOfferwallRewardUseCase registerOfferwallRewardUseCase;
    private final RewardRecordVendorCommunicationHistoryUseCase recordVendorCommunicationHistoryUseCase;
    private final ObjectMapper objectMapper;

    /**
     * 아드팝콘 리워드 지급 콜백 엔드포인트
     *
     * @param rewardKey    리워드 키
     * @param userId       사용자 키
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
    @PostMapping(value = "/adpopcorn/callback")
    public ResponseEntity<String> handleAdpopcornCallback(
            @RequestParam("reward_key") String rewardKey,
            @RequestParam("usn") String userId,
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
        LocalDateTime attendedAt = FormatConverter.parseToLocalDateTime(timeStamp);

        JsonNode payloadJson = buildPayloadJson(
                rewardKey,
                userId,
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
                userId,
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
                true
        );

        RewardVendorCommunicationTargetType targetType = RewardVendorCommunicationTargetType.OFFERWALL;
        RewardVendorName vendorName = RewardVendorName.ADPOPCORN;

        try {
            OfferwallMapper saved = registerOfferwallRewardUseCase.register(command);
            Long targetId = FormatValidator.hasValue(saved) ? saved.getId() : null;

            recordCommunication(targetType, RewardVendorCommunicationType.REQUEST, targetId, vendorName, payloadString, payloadJson, null);

            JsonNode successPayloadJson = buildResponsePayloadJson(true, 1, "success");
            String successPayload = successPayloadJson.toString();

            recordCommunication(targetType, RewardVendorCommunicationType.RESPONSE, targetId, vendorName, successPayload, successPayloadJson, null);

            return ResponseEntity.ok(successPayload);
        } catch (VendorVerificationFailedException e) {
            return handleFailure(targetType, vendorName, payloadString, payloadJson, e, 1100, "invalid signed value");
        } catch (DuplicateOfferwallRewardException e) {
            return handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3100, "duplicate transaction");
        } catch (AuthenticationFailedException e) {
            return handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3200, "invalid user ");
        } catch (Exception e) {
            return handleFailure(targetType, vendorName, payloadString, payloadJson, e, 4000, "custom error message");
        }
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
        putIfNotBlank(node, "campaign_type", campaignType);
        putIfNotBlank(node, "campaign_name", campaignName);
        putIfNotBlank(node, "quantity", quantity);
        putIfNotBlank(node, "signed_value", signedValue);
        putIfNotBlank(node, "app_key", appKey);
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

    private void putIfNotBlank(ObjectNode node, String field, Number value) {
        if (FormatValidator.hasValue(value)) {
            node.put(field, value.toString());
        }
    }

    private ResponseEntity<String> handleFailure(
            RewardVendorCommunicationTargetType targetType,
            RewardVendorName vendorName,
            String requestPayload,
            JsonNode requestPayloadJson,
            Exception e,
            int resultCode,
            String resultMessage
    ) {
        log.error("Exception occured while handling adpopcorn reward: {}", e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        recordCommunication(
                targetType,
                RewardVendorCommunicationType.REQUEST,
                null,
                vendorName,
                requestPayload,
                requestPayloadJson,
                exceptionName
        );

        JsonNode responsePayloadJson = buildResponsePayloadJson(false, resultCode, resultMessage);
        String responsePayload = responsePayloadJson.toString();

        recordCommunication(
                targetType,
                RewardVendorCommunicationType.RESPONSE,
                null,
                vendorName,
                responsePayload,
                responsePayloadJson,
                exceptionName
        );

        return ResponseEntity.ok(responsePayload);
    }

    private void recordCommunication(
            RewardVendorCommunicationTargetType targetType,
            RewardVendorCommunicationType communicationType,
            Long targetId,
            RewardVendorName vendorName,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                RewardVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    private JsonNode buildResponsePayloadJson(boolean isSuccess, int resultCode, String resultMessage) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("Result", isSuccess);
        node.put("ResultCode", resultCode);
        node.put("ResultMsg", resultMessage);

        return node;
    }
}
